package com.omnicrola.panoptes.data.fileIO.xls;

import javax.swing.JOptionPane;

public class ExportSuccessEvent implements Runnable {

    @Override
    public void run() {
        JOptionPane.showMessageDialog(null, "File exported successfully!");
    }

}
