package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.panoptes.control.DataController;

public class NewWorkspaceCommandListener implements ActionListener {

    private final DataController controller;

    public NewWorkspaceCommandListener(DataController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.controller.resetAllTimeblocks();
    }

}
