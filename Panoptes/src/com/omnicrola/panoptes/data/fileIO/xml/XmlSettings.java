package com.omnicrola.panoptes.data.fileIO.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.PersonalData;

@XmlRootElement(name = "settings")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlSettings {

	@XmlElement(name = "WorkStatement")
	public List<WorkStatement> statements;

	@XmlElement(name = "PersonalInfo")
	public PersonalData personalData;

	@XmlElement(name = "Preferences")
	public AppPreferences preferences;
}
