package com.omnicrola.panoptes.data;

public class Project {

    public static final Project NULL = new Project("NULL", "null", "null");
    private final String personalSow;
    private final String invoiceSow;
    private final String name;

    public Project(String name, String invoiceSow, String personalSow) {
        this.name = name;
        this.invoiceSow = invoiceSow;
        this.personalSow = personalSow;
    }

    public String getInvoiceSow() {
        return this.invoiceSow;
    }

    public String getName() {
        return this.name;
    }

    public String getPersonalSow() {
        return this.personalSow;
    }
}
