package com.omnicrola.panoptes.data.fileIO.xls;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsUtilityToolbox {

	public void copyCell(XSSFCell sourceCell, XSSFCell destinationCell) {
		destinationCell.setCellComment(sourceCell.getCellComment());
		destinationCell.setCellStyle(sourceCell.getCellStyle());
		copyCellData(destinationCell, sourceCell);
	}

	public void copyCellData(XSSFCell destinationCell, XSSFCell sourceCell) {
		int cellType = sourceCell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_BOOLEAN:
			destinationCell.setCellValue(sourceCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			destinationCell.setCellValue(sourceCell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			destinationCell.setCellFormula(sourceCell.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			destinationCell.setCellValue(sourceCell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			destinationCell.setCellValue(sourceCell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
		default:
		}
	}

	public void copyRow(XSSFRow source, XSSFRow destination) {
		destination.setHeight(source.getHeight());
		destination.setRowStyle(source.getRowStyle());

		short maxCells = source.getLastCellNum();
		for (int x = 0; x <= maxCells; x++) {
			XSSFCell sourceCell = source.getCell(x, Row.CREATE_NULL_AS_BLANK);
			XSSFCell destinationCell = destination.getCell(x, Row.CREATE_NULL_AS_BLANK);
			copyCell(sourceCell, destinationCell);
		}
	}

	public XSSFCell getCellAt(XSSFWorkbook workbook, int sheet, int row, int column) {
		return workbook.getSheetAt(sheet).getRow(row).getCell(column);
	}

	public String join(String glue, String... elements) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			String singleElement = elements[i];
			stringBuilder.append(singleElement);
			if (i < elements.length - 1) {
				stringBuilder.append(glue);
			}
		}
		return stringBuilder.toString();
	}

	public void setCellBackgroundColor(XSSFWorkbook workbook, XSSFSheet sheet, CellRangeAddress cellRangeAddress,
			short colorPalletIndex) {
		int firstRow = cellRangeAddress.getFirstRow();
		int lastRow = cellRangeAddress.getLastRow();
		int firstColumn = cellRangeAddress.getFirstColumn();
		int lastColumn = cellRangeAddress.getLastColumn();

		XSSFCellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setFillBackgroundColor(colorPalletIndex);
		cellStyle.setFillForegroundColor(colorPalletIndex);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int rowIndex = firstRow; rowIndex <= lastRow; rowIndex++) {
			XSSFRow sheetRow = sheet.getRow(rowIndex);
			for (int cellIndex = firstColumn; cellIndex <= lastColumn; cellIndex++) {
				XSSFCell cell = sheetRow.getCell(cellIndex);
				cell.setCellStyle(cellStyle);
			}
		}
		// sheet.addMergedRegion(cellRangeAddress);
	}

}
