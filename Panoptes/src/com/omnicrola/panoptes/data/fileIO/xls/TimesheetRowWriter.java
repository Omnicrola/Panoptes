package com.omnicrola.panoptes.data.fileIO.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class TimesheetRowWriter {

	private static final String TIMESHEET_ROW_SUM_COLUMN_INDEX_START = "F";
	private static final String TIMESHEET_ROW_SUM_COLUMN_INDEX_END = "L";

	public void writeTimesheetRow(XSSFRow sheetRow, ExportDataRow dataRow) {

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

}
