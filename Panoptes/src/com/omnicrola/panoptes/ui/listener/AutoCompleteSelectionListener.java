package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopup;

public class AutoCompleteSelectionListener implements ActionListener {

    private final AutoCompletePopup autoCompletePopup;
    private final JMenuItem jMenuItem;

    public AutoCompleteSelectionListener(AutoCompletePopup autoCompletePopup, JMenuItem jMenuItem) {
        this.autoCompletePopup = autoCompletePopup;
        this.jMenuItem = jMenuItem;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.autoCompletePopup.setMainField(this.jMenuItem.getText());
    }

}
