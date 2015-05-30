package com.omnicrola.panoptes.data.fileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeblock;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeblockList;

public class FileDataLoader {

    private List<TimeBlock> convertXmlList(XMLTimeblockList xmlBlocklist) {
        List<TimeBlock> timeblocks = new ArrayList<>();

        for (XMLTimeblock xmlBlock : xmlBlocklist.timeblocks) {
            timeblocks.add(xmlBlock.createTimeblock());
        }
        return timeblocks;
    }

    public DateWrapper loadDataFromFile(File source, List<TimeBlock> timeblockBuffer) {
        try {
            Unmarshaller xmlUnmarshaller = JAXBContext.newInstance(XMLTimeblockList.class)
                    .createUnmarshaller();
            XMLTimeblockList xmlBlocklist = (XMLTimeblockList) xmlUnmarshaller.unmarshal(source);
            timeblockBuffer.clear();
            timeblockBuffer.addAll(convertXmlList(xmlBlocklist));

            return xmlBlocklist.weekEnding;
        } catch (JAXBException e) {
            e.printStackTrace();
            return new DateWrapper(new Date());
        }
    }
}
