package com.omnicrola.panoptes.ui.autocomplete;

import javax.swing.JTextField;
import javax.swing.text.Document;

import com.omnicrola.panoptes.ui.listener.AutoCompleteDocumentListener;
import com.omnicrola.panoptes.ui.listener.AutoCompleteKeyListener;

public class AutoCompletePopupFactory {

    public void addAutoComplete(JTextField jTextField, IOptionProvider provider, int maxOptions) {
        Document document = jTextField.getDocument();
        AutoCompletePopup autoCompletePopup = new AutoCompletePopup(maxOptions, jTextField);

        AutoCompleteOptionFilter optionFilter = new AutoCompleteOptionFilter(provider, maxOptions,
                false);
        AutoCompleteDocumentListener autoCompleteDocumentListener = new AutoCompleteDocumentListener(
                document, autoCompletePopup, optionFilter);

        document.addDocumentListener(autoCompleteDocumentListener);
        jTextField.add(autoCompletePopup);

        jTextField.addFocusListener(new AutoCompleteFocusListener(autoCompletePopup));
        jTextField.addKeyListener(new AutoCompleteKeyListener(autoCompletePopup));

    }

}
