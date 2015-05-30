package com.omnicrola.panoptes.ui.personaldata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenPersonalDataDisplayCommand implements ActionListener {

    private final PersonalDataDisplayFactory personalDataDisplayFactory;

    public OpenPersonalDataDisplayCommand(PersonalDataDisplayFactory personalDataDisplayFactory) {
        this.personalDataDisplayFactory = personalDataDisplayFactory;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        PersonalDataDisplay display = this.personalDataDisplayFactory.build();
        display.setVisible(true);
    }
}
