package com.omnicrola.panoptes.data.fileIO.xls;

import javax.swing.JOptionPane;

import com.omnicrola.panoptes.data.fileIO.PanoptesException;

public class ExportFailEvent implements Runnable {

    private final PanoptesException exception;

    public ExportFailEvent(PanoptesException exception) {
        this.exception = exception;
    }

    @Override
    public void run() {
        JOptionPane.showMessageDialog(null, this.exception.getMessage());
    }

}
