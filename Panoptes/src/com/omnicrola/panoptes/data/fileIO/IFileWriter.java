package com.omnicrola.panoptes.data.fileIO;

import java.io.File;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;

public interface IFileWriter {

    public abstract void writeDataToFile(File destination, DateWrapper dateWrapper,
            TimeblockSet allTimeblocks) throws PanoptesException;

}