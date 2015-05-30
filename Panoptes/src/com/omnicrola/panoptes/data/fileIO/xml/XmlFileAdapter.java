package com.omnicrola.panoptes.data.fileIO.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.omnicrola.panoptes.data.fileIO.PanoptesException;

@SuppressWarnings("unchecked")
public class XmlFileAdapter<T> {

    private final Class<T> clazz;

    public XmlFileAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T loadObject(File sourceFile) throws PanoptesException {
        try {
            Unmarshaller xmlUnmarshaller = JAXBContext.newInstance(this.clazz).createUnmarshaller();

            Object unmarshal = xmlUnmarshaller.unmarshal(sourceFile);
            return (T) unmarshal;
        } catch (JAXBException e) {
            throw new PanoptesException("Could not read XML from source file \""
                    + sourceFile.getAbsolutePath() + "\"");
        }
    }

    public void saveObject(Object objectToSave, File destination) throws PanoptesException {
        try {
            Marshaller xmlMarshaller = JAXBContext.newInstance(this.clazz).createMarshaller();
            xmlMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            xmlMarshaller.marshal(objectToSave, destination);
        } catch (JAXBException e) {
            throw new PanoptesException("Could not save XML file \""
                    + destination.getAbsolutePath() + "\"");
        }
    }
}
