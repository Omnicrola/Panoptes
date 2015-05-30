package com.omnicrola.panoptes.ui.listener;

import com.omnicrola.panoptes.data.WorkStatement;

public interface ISowViewUpdater {

    public abstract void currentSelectionChanged(String projectName, String projectCode,
            String client, String sowCode, float billableRate);

    public abstract void statementAdded(WorkStatement workStatement);

    public abstract void statementRemoved(WorkStatement workStatement);

}