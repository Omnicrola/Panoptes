package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.omnicrola.panoptes.ui.AboutDialogBuilder;

public class ShowAboutDialogCommandListener implements ActionListener {

    private final AboutDialogBuilder aboutDialogBuilder;

    public ShowAboutDialogCommandListener(AboutDialogBuilder aboutDialogBuilder) {
        this.aboutDialogBuilder = aboutDialogBuilder;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JDialog aboutDialog = this.aboutDialogBuilder.build();
        aboutDialog.setLocationRelativeTo(null);
        aboutDialog.setVisible(true);
    }

}
