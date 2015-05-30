package com.omnicrola.panoptes.data.fileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeblock;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeblockList;

public class FileDataWriter implements IFileWriter {

    private List<XMLTimeblock> prepareForXml(TimeblockSet allTimeblocks) {
        List<IReadTimeblock> blockSet = allTimeblocks.getBlockSet();
        List<XMLTimeblock> xmlBlocks = new ArrayList<>();

        for (IReadTimeblock timeBlock : blockSet) {
            if (timeBlock.isOccupied()) {
                xmlBlocks.add(new XMLTimeblock(timeBlock));
            }
        }
        return xmlBlocks;
    }

    @Override
    public void writeDataToFile(File destination, DateWrapper weekEnding, TimeblockSet allTimeblocks) {
        XMLTimeblockList xmlTimeblockList = new XMLTimeblockList();
        xmlTimeblockList.timeblocks = prepareForXml(allTimeblocks);
        xmlTimeblockList.weekEnding = weekEnding;

        try {
            Marshaller xmlMarshaller = JAXBContext.newInstance(XMLTimeblockList.class)
                    .createMarshaller();
            xmlMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            xmlMarshaller.marshal(xmlTimeblockList, destination);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
