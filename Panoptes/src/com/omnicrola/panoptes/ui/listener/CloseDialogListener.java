package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class CloseDialogListener implements ActionListener {

    private final JDialog dialog;

    public CloseDialogListener(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.dialog.setVisible(false);
        this.dialog.dispose();
    }

}
