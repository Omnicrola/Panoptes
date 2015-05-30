package com.omnicrola.panoptes.data.fileIO;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XmlFileFilter extends FileFilter {

    public static final XmlFileFilter FILTER = new XmlFileFilter();

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".xml");
    }

    @Override
    public String getDescription() {
        return "XML files";
    }

}
