package com.omnicrola.panoptes.data.fileIO.xls;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JDialog;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;

public class ExportThread extends Thread {

	private final File selectedFile;
	private final ExcelExporter excelExporter;
	private final TimeblockSet allTimeblocks;
	private final JDialog infoDialog;
	private final DateWrapper weekEnding;

	public ExportThread(JDialog infoDialog, File selectedFile, ExcelExporter excelExporter, TimeblockSet allTimeblocks,
			DateWrapper dateWrapper) {
		this.weekEnding = dateWrapper;
		this.setName("XLS export thread");
		this.infoDialog = infoDialog;
		this.selectedFile = selectedFile;
		this.excelExporter = excelExporter;
		this.allTimeblocks = allTimeblocks;
	}

	@Override
	public void run() {
		try {
			this.excelExporter.writeDataToFile(this.selectedFile, this.weekEnding, this.allTimeblocks);
			EventQueue.invokeLater(new ExportSuccessEvent());
		} catch (Exception exception) {
			EventQueue.invokeLater(new ExportFailEvent(exception));
		}
		this.infoDialog.setVisible(false);
		this.infoDialog.dispose();

	}
}
