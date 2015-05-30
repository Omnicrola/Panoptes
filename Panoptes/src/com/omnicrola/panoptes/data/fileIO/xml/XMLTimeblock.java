package com.omnicrola.panoptes.data.fileIO.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeBlock;

@XmlAccessorType(XmlAccessType.FIELD)
public class XMLTimeblock {

    public int blockIndex;
    public int dayIndex;
    public XMLTimeData timeData;

    public XMLTimeblock() {
    }

    public XMLTimeblock(IReadTimeblock timeblock) {
        this.blockIndex = timeblock.getColumnIndex();
        this.dayIndex = timeblock.getDayIndex();
        this.timeData = new XMLTimeData(timeblock.getTimeData());
    }

    public TimeBlock createTimeblock() {
        TimeBlock timeBlock = new TimeBlock(this.dayIndex, this.blockIndex);
        timeBlock.setTimeData(this.timeData.createTimeData());
        timeBlock.setOccupied(true);
        return timeBlock;
    }
}
