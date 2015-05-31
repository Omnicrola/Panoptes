package com.omnicrola.panoptes.ui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.omnicrola.util.ConstructorParameter;

public class TextHighlightingFocusListener implements FocusListener {

	@ConstructorParameter("textField")
	private JTextField textField;

	public TextHighlightingFocusListener(JTextField textField) {
		this.textField = textField;
	}

	@Override
	public void focusGained(FocusEvent event) {
		this.textField.selectAll();
	}

	@Override
	public void focusLost(FocusEvent event) {

	}

}
