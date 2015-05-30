package com.omnicrola.panoptes.ui.personaldata;

import javax.swing.JDialog;

public class PersonalDataDisplay extends JDialog {

    private static final long serialVersionUID = 7028738974911496480L;

    public PersonalDataDisplay() {
        this.setModal(true);
        this.setSize(425, 300);
        this.setTitle("Personal Data");
        this.setResizable(false);
    }
}
