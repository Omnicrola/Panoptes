package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.fileIO.xls.ExcelExporter;
import com.omnicrola.panoptes.data.fileIO.xls.ExportThread;
import com.omnicrola.panoptes.ui.InfoDialog;

public class ExportCommandListener implements ActionListener {

	private final DataController controller;
	private final ExcelExporter excelExporter;
	private final JFileChooser fileChooser;
	private final MainFrame mainFrame;
	private AppSettings appSettings;

	public ExportCommandListener(DataController controller, JFileChooser fileChooser, MainFrame mainFrame,
			ExcelExporter excelExporter, AppSettings appSettings) {
		this.controller = controller;
		this.fileChooser = fileChooser;
		this.mainFrame = mainFrame;
		this.excelExporter = excelExporter;
		this.appSettings = appSettings;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String exportFilename = getExportFilename();

		this.fileChooser.setSelectedFile(new File(exportFilename));
		int result = this.fileChooser.showSaveDialog(this.mainFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = this.fileChooser.getSelectedFile();
			if (selectedFile.exists()) {
				int confirm = JOptionPane.showConfirmDialog(null, "File exists.  Overwrite?");
				if (confirm != JOptionPane.YES_OPTION) {
					return;
				}
			}
			if (!selectedFile.getName().endsWith(".xls")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".xls");
			}
			this.controller.setExportFilename(selectedFile.getName());

			JDialog infoDialog = InfoDialog.createBlockingDialog("Saving...");
			new ExportThread(infoDialog, selectedFile, this.excelExporter, this.controller.getAllTimeblocks(),
					this.controller.getWeekEnding()).start();
			infoDialog.setVisible(true);
		}
	}

	private String getExportFilename() {
		String exportFilename = this.controller.getExportFilename();
		if (this.appSettings.getDefaultExportFilename().equals(exportFilename)) {
			String workspaceFilename = this.controller.getCurrentFilename();
			if (workspaceFilename.endsWith(".xml")) {
				workspaceFilename = workspaceFilename.substring(0, workspaceFilename.length() - 3);
			}
			exportFilename = workspaceFilename + ".xls";
		}
		return exportFilename;
	}

}
