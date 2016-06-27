package com.omnicrola.panoptes.ui.sow;

import javax.swing.JDialog;

public class SowDialog extends JDialog {

	private static final long serialVersionUID = 2652201412371636144L;

	public SowDialog() {
		this.setResizable(false);
		this.setSize(500, 250);
		this.setModal(true);
		this.setLocationRelativeTo(null);
	}
}
