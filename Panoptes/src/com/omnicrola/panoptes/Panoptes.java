package com.omnicrola.panoptes;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.omnicrola.panoptes.bootstrap.BagOfFactories;
import com.omnicrola.panoptes.bootstrap.Singularity;
import com.omnicrola.panoptes.control.DataController;

public class Panoptes {

    public static void main(String[] args) {

        BagOfFactories bagOfFactories = Singularity.bang();

        DataController dataController = bagOfFactories.buildDataController();
        final JFrame mainWindow = bagOfFactories.getMainWindowFactory().build(dataController);

        mainWindow.setSize(800, 600);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);

    }
}
