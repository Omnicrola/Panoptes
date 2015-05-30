package com.omnicrola.panoptes.data.fileIO.xls;

import com.omnicrola.panoptes.data.WorkStatement;

public class InvoiceRow {

    private final WorkStatement workStatement;
    private float totalValue;
    private String description;

    public InvoiceRow(WorkStatement workStatement) {
        this.workStatement = workStatement;
        this.description = workStatement.getProjectName();
    }

    public void addTime(String projectName, float time) {
        this.totalValue += time;
        if (!this.description.contains(projectName)) {
            this.description += "," + projectName;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public float getTotalValue() {
        return this.totalValue;
    }

    public WorkStatement getWorkStatement() {
        return this.workStatement;
    }
}
