package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.fileIO.IFileWriter;
import com.omnicrola.panoptes.data.fileIO.PanoptesException;

public class SaveWorkspaceCommandListener implements ActionListener {

    private final DataController controller;
    private final IFileWriter dataWriter;
    private final JFileChooser fileChooser;
    private final MainFrame mainFrame;

    public SaveWorkspaceCommandListener(DataController controller, IFileWriter dataWriter,
            JFileChooser fileChooser, MainFrame mainFrame) {
        this.controller = controller;
        this.dataWriter = dataWriter;
        this.fileChooser = fileChooser;
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String currentFilename = this.controller.getCurrentFilename();
        this.fileChooser.setSelectedFile(new File(currentFilename));
        int result = this.fileChooser.showSaveDialog(this.mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = this.fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                int confirm = JOptionPane.showConfirmDialog(null, "File exists.  Overwrite?");
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            if (!selectedFile.getName().endsWith(".xml")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
            }

            try {
                this.controller.writeDataToFile(selectedFile, this.dataWriter);
                this.controller.setCurrentFilename(selectedFile.getName());
            } catch (PanoptesException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }

    }

}
