package com.omnicrola.panoptes.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopup;

public class AutoCompleteKeyListener implements KeyListener {

    private final AutoCompletePopup autoCompletePopup;

    public AutoCompleteKeyListener(AutoCompletePopup autoCompletePopup) {
        this.autoCompletePopup = autoCompletePopup;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (this.autoCompletePopup.isVisible()) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                this.autoCompletePopup.moveFocusDown();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                this.autoCompletePopup.moveFocusUp();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                this.autoCompletePopup.updateTextFieldWithSelectedIndex();
                this.autoCompletePopup.setVisible(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

}
