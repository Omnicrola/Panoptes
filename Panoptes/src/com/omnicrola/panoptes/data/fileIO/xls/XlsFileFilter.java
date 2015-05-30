package com.omnicrola.panoptes.data.fileIO.xls;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XlsFileFilter extends FileFilter {

    public static final XlsFileFilter FILTER = new XlsFileFilter();

    private XlsFileFilter() {
    }

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".xls");
    }

    @Override
    public String getDescription() {
        return "XLS files";
    }

}
