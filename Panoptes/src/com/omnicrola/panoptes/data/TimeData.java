package com.omnicrola.panoptes.data;

public class TimeData {

    public static final TimeData NULL = new TimeData("", "", "");
    private final String project;
    private final String card;
    private final String role;

    public TimeData(String project, String card, String role) {
        this.project = project;
        this.card = card;
        this.role = role;
    }

    public String getCard() {
        return this.card;
    }

    public String getProject() {
        return this.project;
    }

    public String getRole() {
        return this.role;
    }
}
