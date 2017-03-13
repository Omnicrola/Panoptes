package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omnicrola.panoptes.data.ProjectGroup;

public class TimesheetDataExporter {

	private static final String PROJECT_SUM_COLUMN_INDEX = "M";
	private static final int TIMESHEET_PROJECT_SUM_COLUMN = 13;

	private final XlsUtilityToolbox toolbox;
	private ExportRowGrouper exportRowGrouper;
	private TimesheetRowWriter timesheetRowWriter;
	private VerticalSumFormulaWriter verticalSumFormulaWriter;

	public TimesheetDataExporter(XlsUtilityToolbox toolbox, ExportRowGrouper exportRowGrouper,
			TimesheetRowWriter timesheetRowWriter, VerticalSumFormulaWriter verticalSumFormulaWriter) {
		this.toolbox = toolbox;
		this.exportRowGrouper = exportRowGrouper;
		this.timesheetRowWriter = timesheetRowWriter;
		this.verticalSumFormulaWriter = verticalSumFormulaWriter;

	}

	private void writeEmptyRow(XSSFWorkbook workbook, XSSFSheet timesheet, XSSFRow sheetRow, ExportDataRow exportRow) {
		for (int i = 1; i <= 15; i++) {
			XSSFCell cell = sheetRow.getCell(i);
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
		int rowNum = sheetRow.getRowNum();
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, rowNum, 1, 12);
		this.toolbox.setCellBackgroundColor(workbook, timesheet, cellRangeAddress, IndexedColors.GREY_80_PERCENT.index);
	}

	private void writeProjectSumFormula(XSSFRow lastRowOfProject, int projectSectionStart, int projectSectionEnd) {
		XSSFCell sumCell = lastRowOfProject.getCell(TIMESHEET_PROJECT_SUM_COLUMN);
		sumCell.setCellFormula("SUM(" + PROJECT_SUM_COLUMN_INDEX + projectSectionStart + ":" + PROJECT_SUM_COLUMN_INDEX
				+ projectSectionEnd + ")");
	}

	public void writeTimesheetData(XSSFWorkbook workbook, List<ExportDataRow> exportList) {
		Map<ProjectGroup, List<ExportDataRow>> groupedRows = this.exportRowGrouper.groupRows(exportList);

		int numberOfNewRows = exportList.size();

		List<ExportDataRow> internalProjectRows = new ArrayList<>(groupedRows.get(ProjectGroup.INTERNAL_PROJECT));
		internalProjectRows.addAll(groupedRows.get(ProjectGroup.INTERNAL_SUPPORT));
		insertRowsForGroup(workbook, internalProjectRows, ExcelExporter.TIMESHEET_INTERNAL_SUPPORT_ROW_INSERT_POSITION);

		insertRowsForGroup(workbook, groupedRows.get(ProjectGroup.CLIENT_BILLABLE),
				ExcelExporter.TIMESHEET_BILLABLE_ROW_INSERT_POSITION);

		this.verticalSumFormulaWriter.reWriteSumFormulas(workbook, numberOfNewRows);
	}

	private int insertRowsForGroup(XSSFWorkbook workbook, List<ExportDataRow> exportList, int insertPosition) {
		int numberOfNewRows = exportList.size();
		if (numberOfNewRows == 0) {
			return 0;
		}
		XSSFSheet timesheet = workbook.getSheetAt(ExcelExporter.SHEET_TIMESHEET);
		timesheet.shiftRows(insertPosition, timesheet.getPhysicalNumberOfRows(), numberOfNewRows, true, true);
		XSSFRow templateRow = timesheet.getRow(insertPosition - 1);

		int currentRow = insertPosition;
		int projectSectionStart = currentRow;
		for (ExportDataRow exportRow : exportList) {
			XSSFRow sheetRow = timesheet.createRow(currentRow);
			this.toolbox.copyRow(templateRow, sheetRow);
			if (exportRow == ExportDataRow.EMPTY) {
				writeProjectSumFormula(timesheet.getRow(currentRow - 1), projectSectionStart, currentRow);
				writeEmptyRow(workbook, timesheet, sheetRow, exportRow);
				projectSectionStart = currentRow + 1;
			} else {
				this.timesheetRowWriter.writeTimesheetRow(sheetRow, exportRow);
				mergeDescriptionCells(timesheet, currentRow);
			}
			currentRow++;
		}
		writeProjectSumFormula(timesheet.getRow(currentRow - 1), projectSectionStart, currentRow);
		removeTemplateRow(timesheet, insertPosition);
		return numberOfNewRows;
	}

	private void mergeDescriptionCells(XSSFSheet timesheet, int currentRow) {
		timesheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 3, 4));
	}

	private void removeTemplateRow(XSSFSheet timesheet, int insertPosition) {
		timesheet.shiftRows(insertPosition, timesheet.getPhysicalNumberOfRows(), -1);
	}

}
