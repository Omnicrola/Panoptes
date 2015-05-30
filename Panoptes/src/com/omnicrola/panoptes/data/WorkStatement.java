package com.omnicrola.panoptes.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class WorkStatement {

    public static final WorkStatement EMPTY = new WorkStatement("None", "", "", "", 0);

    private String projectName;
    private String client;
    private String projectCode;
    private String sowCode;
    private float billableRate;

    public WorkStatement() {
        this.projectName = "";
        this.client = "";
        this.projectCode = "";
        this.sowCode = "";
    }

    public WorkStatement(String projectName, String client, String projectCode, String sowCode,
            float billableRate) {
        this.projectName = projectName;
        this.client = client;
        this.projectCode = projectCode;
        this.sowCode = sowCode;
        this.billableRate = billableRate;
    }

    public float getBillableRate() {
        return this.billableRate;
    }

    public String getClient() {
        return this.client;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getSowCode() {
        return this.sowCode;
    }

    public void setData(String projectName, String client, String projectCode, String sowCode,
            float billableRate) {
        this.projectName = projectName;
        this.client = client;
        this.projectCode = projectCode;
        this.sowCode = sowCode;
        this.billableRate = billableRate;
    }

    @Override
    public String toString() {
        return this.sowCode + " : " + this.projectName + " " + this.projectCode + " " + this.client;
    }
}
