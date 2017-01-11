package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omnicrola.panoptes.data.ProjectGroup;

public class TimesheetDataExporter {

	private static final String TIMESHEET_ROW_SUM_COLUMN_INDEX_START = "F";
	private static final String TIMESHEET_ROW_SUM_COLUMN_INDEX_END = "L";
	private static final String PROJECT_SUM_COLUMN_INDEX = "M";
	private static final int INDEX_OF_TOTALS_ROW = 14;
	private static final int TIMESHEET_PROJECT_SUM_COLUMN = 13;
	private final XlsUtilityToolbox toolbox;

	public TimesheetDataExporter(XlsUtilityToolbox toolbox) {
		this.toolbox = toolbox;
	}

	private void reWriteSumFormulas(XSSFWorkbook workbook, int numberOfNewRows) {
		XSSFSheet timesheet = workbook.getSheetAt(0);
		XSSFRow totalsRow = timesheet.getRow(INDEX_OF_TOTALS_ROW + numberOfNewRows);
		int start = ExcelExporter.TIMESHEET_BILLABLE_ROW_INSERT_POSITION;
		int end = start + numberOfNewRows;
		for (int i = 4; i <= 12; i++) {
			reWriteVerticalSumFormula(totalsRow, i, start, end);
		}
	}

	private void reWriteVerticalSumFormula(XSSFRow totalsRow, int index, int start, int end) {
		char letter = ExcelExporter.ALPHANUMERIC[index];
		XSSFCell cell = totalsRow.getCell(index);
		cell.setCellFormula("SUM(" + letter + start + ":" + letter + end + ")");
	}

	private void setCellValue(XSSFRow row, int columnIndex, float floatValue, boolean clearIfZero) {
		XSSFCell cell = row.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK);
		if (clearIfZero && floatValue == 0.0) {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		} else {
			cell.setCellValue(floatValue);
		}
	}

	private void setCellValue(XSSFRow row, int columnIndex, String stringValue) {
		XSSFCell cell = row.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK);
		cell.setCellValue(stringValue);
	}

	private void writeEmptyRow(XSSFRow sheetRow, ExportDataRow exportRow) {
		for (int i = 1; i <= 15; i++) {
			XSSFCell cell = sheetRow.getCell(i);
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}

	private void writeProjectSumFormula(XSSFRow lastRowOfProject, int projectSectionStart, int projectSectionEnd) {
		XSSFCell sumCell = lastRowOfProject.getCell(TIMESHEET_PROJECT_SUM_COLUMN);
		sumCell.setCellFormula("SUM(" + PROJECT_SUM_COLUMN_INDEX + projectSectionStart + ":" + PROJECT_SUM_COLUMN_INDEX
				+ projectSectionEnd + ")");
	}

	public void writeTimesheetData(XSSFWorkbook workbook, List<ExportDataRow> exportList) {

		XSSFSheet timesheet = workbook.getSheetAt(ExcelExporter.SHEET_TIMESHEET);

		List<ExportDataRow> billableRows = filterProjectGroup(exportList, ProjectGroup.CLIENT_BILLABLE);
		List<ExportDataRow> internalProjectRows = filterProjectGroup(exportList, ProjectGroup.INTERNAL_PROJECT);
		List<ExportDataRow> internalSupportRows = filterProjectGroup(exportList, ProjectGroup.INTERNAL_SUPPORT);

		int numberOfNewRows = 0;
		numberOfNewRows += insertRowsForGroup(internalSupportRows, timesheet,
				ExcelExporter.TIMESHEET_INTERNAL_SUPPORT_ROW_INSERT_POSITION);
		numberOfNewRows += insertRowsForGroup(internalProjectRows, timesheet,
				ExcelExporter.TIMESHEET_INTERNAL_PROJECT_ROW_INSERT_POSITION);
		numberOfNewRows += insertRowsForGroup(billableRows, timesheet,
				ExcelExporter.TIMESHEET_BILLABLE_ROW_INSERT_POSITION);

		reWriteSumFormulas(workbook, numberOfNewRows);
	}

	private List<ExportDataRow> filterProjectGroup(List<ExportDataRow> exportList, ProjectGroup projectGroup) {
		return exportList.stream().filter(r -> projectGroup.equals(r.getWorkStatement().getProjectGroup()))
				.collect(Collectors.toList());
	}

	private int insertRowsForGroup(List<ExportDataRow> exportList, XSSFSheet timesheet, int insertPosition) {
		int numberOfNewRows = exportList.size();
		if (numberOfNewRows == 0) {
			return 0;
		}

		timesheet.shiftRows(insertPosition, timesheet.getPhysicalNumberOfRows(), numberOfNewRows, true, true);
		XSSFRow templateRow = timesheet.getRow(insertPosition - 1);

		int currentRow = insertPosition;
		int projectSectionStart = currentRow;
		for (ExportDataRow exportRow : exportList) {
			XSSFRow sheetRow = timesheet.createRow(currentRow);
			this.toolbox.copyRow(templateRow, sheetRow);
			if (exportRow == ExportDataRow.EMPTY) {
				writeProjectSumFormula(timesheet.getRow(currentRow - 1), projectSectionStart, currentRow);
				writeEmptyRow(sheetRow, exportRow);
				projectSectionStart = currentRow + 1;
			} else {
				writeTimesheetRow(sheetRow, exportRow);
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

	private void writeTimesheetRow(XSSFRow sheetRow, ExportDataRow dataRow) {

		// column 0 is empty
		setCellValue(sheetRow, 1, dataRow.getWorkStatement().getClient());
		setCellValue(sheetRow, 2, dataRow.getWorkStatement().getProjectCode());
		setCellValue(sheetRow, 3, dataRow.getDescription());

		// column 4 is merged with column 3

		setCellValue(sheetRow, 5, dataRow.getDay(0), true);
		setCellValue(sheetRow, 6, dataRow.getDay(1), true);
		setCellValue(sheetRow, 7, dataRow.getDay(2), true);
		setCellValue(sheetRow, 8, dataRow.getDay(3), true);
		setCellValue(sheetRow, 9, dataRow.getDay(4), true);
		setCellValue(sheetRow, 10, dataRow.getDay(5), true);
		setCellValue(sheetRow, 11, dataRow.getDay(6), true);

		int rowIndex = sheetRow.getRowNum() + 1;
		sheetRow.getCell(12).setCellFormula(
				"SUM(" + TIMESHEET_ROW_SUM_COLUMN_INDEX_START + rowIndex + ":" + TIMESHEET_ROW_SUM_COLUMN_INDEX_END
						+ rowIndex + ")");
	}

}
