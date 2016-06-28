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

	private void writeInvoicePersonalInfo(XSSFWorkbook workbook, IReadPersonalData personalData) {
		String phone = personalData.getPhone();
		String email = personalData.getEmail();

		String addressBlock = buildAddress(personalData);

		String payableTo = "Make all checks payable to " + getPayableName(personalData);

		XSSFCell nameCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 2, 0);
		XSSFCell addressCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 3, 0);
		XSSFCell phoneCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 4, 0);
		XSSFCell emailCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 5, 0);
		XSSFCell payableCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 20, 0);

		nameCell.setCellValue(getPayableName(personalData));
		addressCell.setCellValue(addressBlock);
		phoneCell.setCellValue(phone);
		emailCell.setCellValue(email);
		payableCell.setCellValue(payableTo);

	}

	private String buildAddress(IReadPersonalData personalData) {
		boolean hasLLC = personalData.getCompanyName().trim().length() > 0;
		String streetAddress = personalData.getStreetAddress();
		String addressLine2 = personalData.getCity() + ", " + personalData.getState() + " " + personalData.getZip();
		if (hasLLC) {
			String fullName = personalData.getFullName();
			return this.toolbox.join("\n", fullName, streetAddress, addressLine2);
		} else {
			return this.toolbox.join("\n", streetAddress, addressLine2);
		}
	}

	private String getPayableName(IReadPersonalData personalData) {
		String companyName = personalData.getCompanyName().trim();
		if (companyName.length() > 0) {
			return companyName;
		} else {
			return personalData.getFullName();
		}
	}

	public void writePersonalData(XSSFWorkbook workbook, IReadPersonalData personalData, DateWrapper weekEnding) {
		writeWorksheetPersonalInfo(workbook, personalData, weekEnding);
		writeInvoicePersonalInfo(workbook, personalData);

	}

	private void writeWorksheetPersonalInfo(XSSFWorkbook workbook, IReadPersonalData personalData,
			DateWrapper weekEnding) {
		XSSFCell nameCell = getNameCell(workbook);
		XSSFCell weekEndingCell = getWeekEndingCell(workbook);

		nameCell.setCellValue(personalData.getFullName() + " " + personalData.getCompanyName());
		weekEndingCell.setCellValue(weekEnding.getDate());
	}

}
