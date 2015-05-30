package com.omnicrola.panoptes.data.fileIO.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.omnicrola.panoptes.data.PersonalData;
import com.omnicrola.panoptes.data.WorkStatement;

@XmlRootElement(name = "settings")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLSettings {

    @XmlElement(name = "WorkStatement")
    public List<WorkStatement> statements;

    @XmlElement(name = "PersonalInfo")
    public PersonalData personalData;
}
