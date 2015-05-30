package com.omnicrola.panoptes.ui.autocomplete;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AutoCompleteFocusListener implements FocusListener {

    private final AutoCompletePopup autoCompletePopup;

    public AutoCompleteFocusListener(AutoCompletePopup autoCompletePopup) {
        this.autoCompletePopup = autoCompletePopup;
    }

    @Override
    public void focusGained(FocusEvent arg0) {
    }

    @Override
    public void focusLost(FocusEvent event) {
        this.autoCompletePopup.setVisible(false);
    }

}
