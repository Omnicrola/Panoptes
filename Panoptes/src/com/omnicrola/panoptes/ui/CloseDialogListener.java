package com.omnicrola.panoptes.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.util.ConstructorParameter;

public class CloseDialogListener implements ActionListener {

	@ConstructorParameter("dialog")
	private IDialog dialog;

	public CloseDialogListener(IDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dialog.closeDisplay();
	}

}
