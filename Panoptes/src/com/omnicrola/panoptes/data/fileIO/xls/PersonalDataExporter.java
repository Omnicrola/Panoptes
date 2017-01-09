package com.omnicrola.panoptes.data.fileIO.xls;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.IReadPersonalData;

public class PersonalDataExporter {

	private final XlsUtilityToolbox toolbox;

	public PersonalDataExporter(XlsUtilityToolbox toolbox) {
		this.toolbox = toolbox;
	}

	private XSSFCell getNameCell(XSSFWorkbook workbook) {
		return this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_TIMESHEET, 1, 2);
	}

	private XSSFCell getWeekEndingCell(XSSFWorkbook workbook) {
		return this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_TIMESHEET, 2, 2);
	}

	public void writePersonalData(XSSFWorkbook workbook, IReadPersonalData personalData, DateWrapper weekEnding) {
		writeWorksheetPersonalInfo(workbook, personalData, weekEnding);
	}

	private void writeWorksheetPersonalInfo(XSSFWorkbook workbook, IReadPersonalData personalData,
			DateWrapper weekEnding) {
		XSSFCell nameCell = getNameCell(workbook);
		XSSFCell weekEndingCell = getWeekEndingCell(workbook);

		nameCell.setCellValue(personalData.getFullName() + " " + personalData.getCompanyName());
		weekEndingCell.setCellValue(weekEnding.getDate());
	}

}
