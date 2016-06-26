package com.omnicrola.panoptes.data.fileIO.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.omnicrola.panoptes.data.DateWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SavedBlocks")
public class OldXmlTimeblockList {

	@XmlElement(name = "weekEnding")
	public DateWrapper weekEnding = new DateWrapper(new Date());

	@XmlElement(name = "block")
	public List<XMLTimeblock> timeblocks = new ArrayList<>();

}
