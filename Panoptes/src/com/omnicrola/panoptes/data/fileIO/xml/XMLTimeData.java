package com.omnicrola.panoptes.data.fileIO.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.omnicrola.panoptes.data.TimeData;

@XmlAccessorType(XmlAccessType.FIELD)
public class XMLTimeData {

    public String card;
    public String project;
    public String role;

    public XMLTimeData() {
    }

    public XMLTimeData(TimeData data) {
        this.card = data.getCard();
        this.project = data.getProject();
        this.role = data.getRole();
    }

    public TimeData createTimeData() {
        return new TimeData(this.project, this.card, this.role);
    }

}
