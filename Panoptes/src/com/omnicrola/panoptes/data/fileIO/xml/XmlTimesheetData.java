package com.omnicrola.panoptes.data.fileIO.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.settings.AppSettings;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Timesheet")
public class XmlTimesheetData {

	@XmlAttribute(name = "formatVersion")
	public int fileVersion = AppSettings.INSTANCE.getCurrentFileFormatVersion();

	@XmlElement(name = "weekEnding")
	public DateWrapper weekEnding = new DateWrapper(new Date());

	@XmlElement(name = "block")
	@XmlElementWrapper(name = "blocks")
	public List<XMLTimeblock> timeblocks = new ArrayList<>();

}
