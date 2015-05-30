package com.omnicrola.panoptes.data.fileIO.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class XlsTemplateFunctionalTest {

	private XSSFWorkbook workbook;
	private FileInputStream templateFileStream;

	@Before
	public void getTemplateFile() throws Exception {
		this.templateFileStream = new FileInputStream(new File("../Panoptes/resources/invoiceTemplate.xlsx"));
		this.workbook = new XSSFWorkbook(this.templateFileStream);
	}

	@After
	public void closeFile() throws Exception {
		this.templateFileStream.close();
	}

	@Test
	public void testCell() throws Exception {
		XlsUtilityToolbox toolbox = new XlsUtilityToolbox();

		XSSFSheet timesheet = this.workbook.getSheetAt(0);
		timesheet.shiftRows(9, 100, 5, true, true);
		XSSFRow templateRow = timesheet.getRow(8);
		for (int i = 9; i < 15; i++) {
			XSSFRow newRow = timesheet.createRow(i);
			toolbox.copyRow(templateRow, newRow);
		}

		FileOutputStream outFile = new FileOutputStream(new File("resources/testExport.xlsx"));
		this.workbook.write(outFile);
		outFile.close();
	}

	@Test
	public void testLoadXls() throws Exception {

		XSSFSheet sheet = this.workbook.getSheetAt(0);

		for (int i = 0; i < 18; i++) {
			System.out.print(sheet.getColumnWidth(i) + " ");
		}
		System.out.println();
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue() + "\t\t");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue() + "\t\t");
					break;
				case Cell.CELL_TYPE_FORMULA:
					System.out.print(cell.getCellFormula() + "\t\t");
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + "\t\t");
					break;
				}
			}
			System.out.println();
		}

	}

}
