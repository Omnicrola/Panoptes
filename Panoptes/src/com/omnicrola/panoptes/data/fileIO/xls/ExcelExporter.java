package com.omnicrola.panoptes.data.fileIO.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.fileIO.IFileWriter;
import com.omnicrola.panoptes.data.fileIO.PanoptesException;

public class ExcelExporter implements IFileWriter {

	private static final String INVOICE_TEMPLATE_FILE = "/resources/InvoiceTemplate.xlsx";

	static final char[] ALPHANUMERIC = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	static final int TIMESHEET_BILLABLE_ROW_INSERT_POSITION = 8;
	static final int TIMESHEET_INTERNAL_PROJECT_ROW_INSERT_POSITION = 13;
	static final int TIMESHEET_INTERNAL_SUPPORT_ROW_INSERT_POSITION = 19;

	static final int INVOICE_ROW_INSERTION_POSITION = 17;
	static final int SHEET_TIMESHEET = 0;

	private final ExportModelBuilder exportModelBuilder;
	private final DataController controller;
	private final PersonalDataExporter personalDataExporter;
	private final TimesheetDataExporter dataExporter;

	public ExcelExporter(DataController controller, ExportModelBuilder exportModelBuilder,
			PersonalDataExporter personalDataWriter, TimesheetDataExporter dataExporter) {
		this.controller = controller;
		this.exportModelBuilder = exportModelBuilder;
		this.personalDataExporter = personalDataWriter;
		this.dataExporter = dataExporter;
	}

	private void actuallySaveFile(File destination, XSSFWorkbook workbook) throws PanoptesException {
		destination.delete();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(destination);
			workbook.write(fileOutputStream);
		} catch (FileNotFoundException e) {
			throw new PanoptesException("Export error : file not found.");
		} catch (IOException e) {
			throw new PanoptesException(
					"Export failed.\nAn I/O error was encountered while writing to the destination file : "
							+ destination.getAbsolutePath());
		}
	}

	private XSSFWorkbook loadInvoiceTemplate() throws PanoptesException {
		try {
			InputStream fileInputStream = getClass().getResourceAsStream(INVOICE_TEMPLATE_FILE);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
			return xssfWorkbook;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new PanoptesException("Export failed.\nThe invoice template \"" + INVOICE_TEMPLATE_FILE
					+ "\" could not be found.");
		} catch (IOException e) {
			throw new PanoptesException(
					"Export failed.\nAn I/O error was encountered while reading the inoice template file.");
		}
	}

	private void refreshFormulas(XSSFWorkbook workbook) {
		XSSFFormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Iterator<XSSFSheet> sheetIterator = workbook.iterator();
		while (sheetIterator.hasNext()) {
			Iterator<Row> rowIterator = sheetIterator.next().iterator();
			while (rowIterator.hasNext()) {
				Iterator<Cell> cellIterator = rowIterator.next().iterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					formulaEvaluator.evaluateFormulaCell(cell);
				}
			}
		}
	}

	@Override
	public void writeDataToFile(File destination, DateWrapper weekEnding, TimeblockSet timeblocks)
			throws PanoptesException {

		XSSFWorkbook workbook = loadInvoiceTemplate();
		List<ExportDataRow> exportList = this.exportModelBuilder.buildDataRows(timeblocks);

		if (exportList.isEmpty()) {
			throw new PanoptesException("No data to save!");
		} else {
			this.personalDataExporter.writePersonalData(workbook, this.controller.getPersonalData(), weekEnding);
			this.dataExporter.writeTimesheetData(workbook, exportList);

			refreshFormulas(workbook);
			actuallySaveFile(destination, workbook);
		}
	}
}
