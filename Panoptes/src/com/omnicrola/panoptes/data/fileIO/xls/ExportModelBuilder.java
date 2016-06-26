package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.WorkStatement;

public class ExportModelBuilder {

    private final DataController controller;

    public ExportModelBuilder(DataController controller) {
        this.controller = controller;
    }

    public List<ExportDataRow> buildDataRows(TimeblockSet timeblocks) {
        List<IReadTimeblock> blockSet = timeblocks.getBlockSet();
        HashMap<String, WorkStatement> projectNameMap = buildWorkstatementHashMap(this.controller
                .getWorkStatements());
        HashMap<String, ExportDataRow> rowMap = new HashMap<String, ExportDataRow>();

        for (IReadTimeblock timeBlock : blockSet) {
            if (timeBlock.getTimeData() != TimeData.NULL) {
                ExportDataRow dataRow = getRow(rowMap, timeBlock, projectNameMap);
                int dayIndex = timeBlock.getDayIndex();
                dataRow.addTime(dayIndex, 0.25f);
            }
        }

        List<ExportDataRow> dataList = new ArrayList<ExportDataRow>(rowMap.values());
        Collections.sort(dataList, new ExportRowComparator());
        insertBlankRows(dataList);
        return dataList;
    }

    public HashMap<String, InvoiceRow> buildInvoiceRows(XSSFWorkbook workbook,
            List<ExportDataRow> exportList) {
        HashMap<String, InvoiceRow> sowToRowMap = new HashMap<>();

        for (ExportDataRow exportDataRow : exportList) {
            if (exportDataRow != ExportDataRow.EMPTY) {
                String projectCode = exportDataRow.getWorkStatement().getProjectCode();
                String projectName = exportDataRow.getWorkStatement().getProjectName();

                if (sowToRowMap.containsKey(projectCode)) {
                    InvoiceRow invoiceRow = sowToRowMap.get(projectCode);
                    invoiceRow.addTime(projectName, exportDataRow.getTotalTime());
                } else {
                    WorkStatement workStatement = exportDataRow.getWorkStatement();
                    InvoiceRow invoiceRow = new InvoiceRow(workStatement);
                    invoiceRow.addTime(projectName, exportDataRow.getTotalTime());
                    sowToRowMap.put(projectCode, invoiceRow);
                }
            }
        }
        return sowToRowMap;
    }

    private HashMap<String, WorkStatement> buildWorkstatementHashMap(
            List<WorkStatement> workStatements) {
        HashMap<String, WorkStatement> clientToSowMap = new HashMap<String, WorkStatement>();
        for (WorkStatement workStatement : workStatements) {
            String projectName = workStatement.getProjectName();
            clientToSowMap.put(projectName, workStatement);
        }
        return clientToSowMap;
    }

    private ExportDataRow createNewRow(HashMap<String, ExportDataRow> rowMap,
            IReadTimeblock timeBlock, HashMap<String, WorkStatement> projectNameMap) {
        String project = timeBlock.getTimeData().getProject();

        WorkStatement workStatement = WorkStatement.EMPTY;
        if (projectNameMap.containsKey(project)) {
            workStatement = projectNameMap.get(project);
        }

        String card = timeBlock.getTimeData().getCard();
        String role = timeBlock.getTimeData().getRole();

        ExportDataRow exportDataRow = new ExportDataRow(workStatement, project, role, card);
        rowMap.put(getHashKey(timeBlock), exportDataRow);
        return exportDataRow;
    }

    private String getHashKey(IReadTimeblock timeblock) {
        String project = timeblock.getTimeData().getProject();
        String card = timeblock.getTimeData().getCard();
        String hashKey = project + card;
        return hashKey;

    }

    private ExportDataRow getRow(HashMap<String, ExportDataRow> rowMap, IReadTimeblock timeBlock,
            HashMap<String, WorkStatement> projectNameMap) {
        String hashKey = getHashKey(timeBlock);
        if (rowMap.containsKey(hashKey)) {
            return rowMap.get(hashKey);
        }
        ExportDataRow exportDataRow = createNewRow(rowMap, timeBlock, projectNameMap);

        return exportDataRow;
    }

    private void insertBlankRows(List<ExportDataRow> dataList) {
        List<Integer> list = new ArrayList<>();

        if (!dataList.isEmpty()) {
            String lastProject = dataList.get(0).getWorkStatement().getProjectName();

            for (ExportDataRow exportDataRow : dataList) {
                String projectName = exportDataRow.getWorkStatement().getProjectName();
                if (!projectName.equals(lastProject)) {
                    lastProject = projectName;
                    list.add(dataList.indexOf(exportDataRow));
                }
            }

            int arrayEnd = list.size() - 1;
            for (int i = arrayEnd; i >= 0; i--) {
                Integer index = list.get(i);
                dataList.add(index, ExportDataRow.EMPTY);
            }
        }
    }
}
