package com.omnicrola.panoptes.data.fileIO.xls;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class VerticalSumFormulaWriter {

	private static final int INDEX_OF_TOTALS_ROW = 14;
	private static final int VERTICAL_SUM_COLUMN_START = 4;
	private static final int VERTICAL_SUM_COLUMN_END = 12;

	public void reWriteSumFormulas(XSSFWorkbook workbook, int numberOfNewRows) {
		XSSFSheet timesheet = workbook.getSheetAt(0);
		XSSFRow totalsRow = timesheet.getRow(INDEX_OF_TOTALS_ROW + numberOfNewRows);
		int start = ExcelExporter.TIMESHEET_BILLABLE_ROW_INSERT_POSITION;
		int end = start + numberOfNewRows;
		for (int i = VERTICAL_SUM_COLUMN_START; i <= VERTICAL_SUM_COLUMN_END; i++) {
			reWriteVerticalSumFormula(totalsRow, i, start, end);
		}
	}

	private void reWriteVerticalSumFormula(XSSFRow totalsRow, int index, int start, int end) {
		char letter = ExcelExporter.ALPHANUMERIC[index];
		XSSFCell cell = totalsRow.getCell(index);
		cell.setCellFormula("SUM(" + letter + start + ":" + letter + end + ")");
	}

}
