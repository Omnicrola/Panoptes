package com.omnicrola.panoptes.data.fileIO.xls;

import javax.swing.JOptionPane;

public class ExportFailEvent implements Runnable {

	private final Exception exception;

	public ExportFailEvent(Exception exception) {
		this.exception = exception;
	}

	@Override
	public void run() {
		String errorMessage = "An error occured while exporting the Excel workbook.\nCaught a "
				+ this.exception.getClass().getSimpleName() + " exception.\nMessage was: "
				+ this.exception.getMessage();
		JOptionPane.showMessageDialog(null, errorMessage);
	}

}
