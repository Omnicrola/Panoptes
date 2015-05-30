package com.omnicrola.panoptes.ui.listener;

import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.omnicrola.panoptes.ui.autocomplete.AutoCompleteOptionFilter;
import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopup;

public class AutoCompleteDocumentListener implements DocumentListener {

    private final AutoCompletePopup autoCompletePopup;
    private final AutoCompleteOptionFilter optionProvider;
    private final Document textFieldDocument;

    public AutoCompleteDocumentListener(Document document, AutoCompletePopup autoCompletePopup,
            AutoCompleteOptionFilter optionProvider) {
        this.textFieldDocument = document;
        this.autoCompletePopup = autoCompletePopup;
        this.optionProvider = optionProvider;
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        updatePopup();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updatePopup();
    }

    private void updatePopup() {
        try {
            int length = this.textFieldDocument.getLength();
            String text = this.textFieldDocument.getText(0, length);
            if (text.length() > 1) {
                List<String> options = this.optionProvider.findOptions(text);
                this.autoCompletePopup.setOptions(options);
            } else {
                this.autoCompletePopup.setVisible(false);
            }
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
    }

}
