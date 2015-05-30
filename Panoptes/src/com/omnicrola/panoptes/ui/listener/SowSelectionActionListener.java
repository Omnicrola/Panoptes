package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.ui.sow.SowModelPresenter;

public class SowSelectionActionListener implements ActionListener {

    private final JComboBox<WorkStatement> workStatementDropdown;
    private final SowModelPresenter sowModelPresenter;

    public SowSelectionActionListener(JComboBox<WorkStatement> workStatementDropdown,
            SowModelPresenter sowModelPresenter) {
        this.workStatementDropdown = workStatementDropdown;
        this.sowModelPresenter = sowModelPresenter;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        WorkStatement workStatement = (WorkStatement) this.workStatementDropdown.getSelectedItem();
        this.sowModelPresenter.setCurrentWorkStatement(workStatement);
    }

}
