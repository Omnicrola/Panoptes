package com.omnicrola.panoptes.data.fileIO.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.TimeBlock;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SavedBlocks")
public class XMLTimeblockList {

	@XmlElement(name = "weekEnding")
	public DateWrapper weekEnding = new DateWrapper(new Date());

	@XmlElement(name = "block")
	public List<XMLTimeblock> timeblocks = new ArrayList<>();

	public List<TimeBlock> createTimeblocks() {
		List<TimeBlock> timeblocks = new ArrayList<>();
		for (XMLTimeblock xmlBlock : this.timeblocks) {
			timeblocks.add(xmlBlock.createTimeblock());
		}
		return timeblocks;
	}
}
