package com.omnicrola.panoptes.bootstrap;

import com.omnicrola.panoptes.ShutdownSequence;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;

public class ShutdownSequenceFactory {

    public void buildAndAttachShutdownToRuntime(SettingsFileManager workStatementFileManagerLoader,
            MainDataModel mainDataModel) {
        ShutdownSequence shutdownSequence = new ShutdownSequence(workStatementFileManagerLoader,
                mainDataModel);
        Runtime.getRuntime().addShutdownHook(shutdownSequence);
    }

}
