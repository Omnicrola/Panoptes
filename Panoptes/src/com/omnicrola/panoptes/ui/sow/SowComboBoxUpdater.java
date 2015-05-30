package com.omnicrola.panoptes.ui.sow;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.ui.SelfSortingComboBoxModel;
import com.omnicrola.panoptes.ui.listener.ISowViewUpdater;

public class SowComboBoxUpdater implements ISowViewUpdater {

    private final SelfSortingComboBoxModel<WorkStatement> selfSortingModel;

    public SowComboBoxUpdater(SelfSortingComboBoxModel<WorkStatement> selfSortingModel) {
        this.selfSortingModel = selfSortingModel;
    }

    @Override
    public void currentSelectionChanged(String projectName, String projectCode, String client,
            String sowCode, float rate) {
    }

    @Override
    public void statementAdded(WorkStatement workStatement) {
        this.selfSortingModel.addElement(workStatement);
    }

    @Override
    public void statementRemoved(WorkStatement workStatement) {
        this.selfSortingModel.removeElement(workStatement);
    }
}
