package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.fileIO.FileDataLoader;

public class OpenWorkspaceListener implements ActionListener {

    private final DataController controller;
    private final FileDataLoader fileDataLoader;
    private final JFileChooser fileChooser;
    private final MainFrame mainFrame;

    public OpenWorkspaceListener(DataController controller, FileDataLoader fileDataLoader,
            JFileChooser fileChooser, MainFrame mainFrame) {
        this.controller = controller;
        this.fileDataLoader = fileDataLoader;
        this.fileChooser = fileChooser;
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = this.fileChooser.showOpenDialog(this.mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File source = this.fileChooser.getSelectedFile();
            List<TimeBlock> blockFromFile = new ArrayList<>();
            DateWrapper weekEnding = this.fileDataLoader.loadDataFromFile(source, blockFromFile);

            this.controller.resetAllTimeblocks();
            this.controller.overwriteSelectedBlocks(blockFromFile);
            this.controller.setCurrentFilename(source.getName());
            this.controller.setWeekEnding(weekEnding);
        }
    }

}
