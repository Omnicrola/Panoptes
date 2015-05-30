package com.omnicrola.panoptes.ui.listener;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.omnicrola.panoptes.data.WorkStatement;

public class SowTextFieldUpdater implements ISowViewUpdater {

    private final JTextField projectNameField;
    private final JTextField clientField;
    private final JTextField sowField;
    private final JTextField projectCodeField;
    private final JFormattedTextField rateField;

    public SowTextFieldUpdater(JTextField projectNameField, JTextField clientField,
            JTextField projectCodeField, JTextField sowField, JFormattedTextField rateField) {
        this.projectNameField = projectNameField;
        this.clientField = clientField;
        this.projectCodeField = projectCodeField;
        this.sowField = sowField;
        this.rateField = rateField;
    }

    @Override
    public void currentSelectionChanged(String projectName, String projectCode, String client,
            String sowCode, float billableRate) {
        this.projectNameField.setText(projectName);
        this.clientField.setText(client);
        this.projectCodeField.setText(projectCode);
        this.sowField.setText(sowCode);
        this.rateField.setText(String.valueOf(billableRate));
    }

    @Override
    public void statementAdded(WorkStatement workStatement) {
    }

    @Override
    public void statementRemoved(WorkStatement workStatement) {
    }

}
