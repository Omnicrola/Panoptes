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


    private XSSFCell getCompanyCell(XSSFWorkbook workbook) {
        return this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_TIMESHEET, 2, 2);
    }

    private XSSFCell getNameCell(XSSFWorkbook workbook) {
        return this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_TIMESHEET, 1, 2);
    }

    private XSSFCell getWeekEndingCell(XSSFWorkbook workbook) {
        return this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_TIMESHEET, 3, 2);
    }

    private void writeInvoicePersonalInfo(XSSFWorkbook workbook, IReadPersonalData personalData,
            String fullName) {
        String address = personalData.getAddress();
        String city = personalData.getCity();
        String state = personalData.getState();
        String zip = personalData.getZip();
        String phone = personalData.getPhone();
        String email = personalData.getEmail();

        String address2 = city + ", " + state + " " + zip;
        String addressBlock = this.toolbox.join("\n", address, address2, phone, " ", email);
        String payableTo = "Makes all checks payable to " + fullName;

        XSSFCell nameCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 2, 0);
        XSSFCell addressCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 3, 0);
        XSSFCell payableCell = this.toolbox.getCellAt(workbook, ExcelExporter.SHEET_INVOICE, 22, 0);

        nameCell.setCellValue(fullName);
        addressCell.setCellValue(addressBlock);
        payableCell.setCellValue(payableTo);

    }

    public void writePersonalData(XSSFWorkbook workbook, IReadPersonalData personalData,
            DateWrapper weekEnding) {
        String firstName = personalData.getFirstName();
        String lastname = personalData.getLastName();
        String fullName = firstName + " " + lastname;

        writeWorksheetPersonalInfo(workbook, personalData, fullName, weekEnding);
        writeInvoicePersonalInfo(workbook, personalData, fullName);

    }

    private void writeWorksheetPersonalInfo(XSSFWorkbook workbook, IReadPersonalData personalData,
            String fullName, DateWrapper weekEnding) {
        XSSFCell nameCell = getNameCell(workbook);
        XSSFCell companyCell = getCompanyCell(workbook);
        XSSFCell weekEndingCell = getWeekEndingCell(workbook);

        nameCell.setCellValue(fullName);
        companyCell.setCellValue(personalData.getCompanyName());
        weekEndingCell.setCellValue(weekEnding.getDate());

    }

}
