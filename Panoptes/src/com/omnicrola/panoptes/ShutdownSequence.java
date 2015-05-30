package com.omnicrola.panoptes;

import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;

public class ShutdownSequence extends Thread {

    private final SettingsFileManager workStatementFileManager;
    private final MainDataModel mainDataModel;

    public ShutdownSequence(SettingsFileManager workStatementFileManager,
            MainDataModel mainDataModel) {
        this.workStatementFileManager = workStatementFileManager;
        this.mainDataModel = mainDataModel;
    }

    @Override
    public void run() {
        this.workStatementFileManager.save(this.mainDataModel.getWorkStatements(),
                this.mainDataModel.getPersonalData());
    }

}
