package com.omnicrola.panoptes.data.fileIO.xls;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class VerticalSumFormulaWriter {

	private static final int INDEX_OF_TOTALS_ROW = 12;
	private static final int VERTICAL_SUM_COLUMN_START = 5;
	private static final int VERTICAL_SUM_COLUMN_END = 11;
	private static final int GAP_BETWEEN_BILLABLE_AND_INTERNAL_SECTIONS = 3;

	public void reWriteSumFormulas(XSSFWorkbook workbook, int numberOfNewRows) {
		XSSFSheet timesheet = workbook.getSheetAt(0);
		XSSFRow totalsRow = timesheet.getRow(INDEX_OF_TOTALS_ROW + numberOfNewRows);
		int startingRowIndex = ExcelExporter.TIMESHEET_BILLABLE_ROW_INSERT_POSITION;
		int endingRowIndex = startingRowIndex + numberOfNewRows + GAP_BETWEEN_BILLABLE_AND_INTERNAL_SECTIONS;
		for (int i = VERTICAL_SUM_COLUMN_START; i <= VERTICAL_SUM_COLUMN_END; i++) {
			reWriteVerticalSumFormula(totalsRow, i, startingRowIndex, endingRowIndex);
		}
	}

	private XSSFCell reWriteVerticalSumFormula(XSSFRow totalsRow, int index, int start, int end) {
		char letter = ExcelExporter.ALPHANUMERIC[index];
		XSSFCell cell = totalsRow.getCell(index);
		cell.setCellFormula("SUM(" + letter + start + ":" + letter + end + ")");
		return cell;

	}

}
