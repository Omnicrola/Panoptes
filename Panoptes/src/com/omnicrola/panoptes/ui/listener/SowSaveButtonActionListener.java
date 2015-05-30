package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.omnicrola.panoptes.ui.sow.SowModelPresenter;

public class SowSaveButtonActionListener implements ActionListener {

    private final JTextField projectNameField;
    private final JTextField clientField;
    private final JTextField projectCodeField;
    private final JTextField sowField;
    private final SowModelPresenter sowModelPresenter;
    private final JFormattedTextField rateField;

    public SowSaveButtonActionListener(SowModelPresenter sowModelPresenter,
            JTextField projectNameField, JTextField clientField, JTextField projectCodeField,
            JTextField sowField, JFormattedTextField rateField) {
        this.sowModelPresenter = sowModelPresenter;
        this.projectNameField = projectNameField;
        this.clientField = clientField;
        this.projectCodeField = projectCodeField;
        this.sowField = sowField;
        this.rateField = rateField;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String projectName = this.projectNameField.getText();
        String client = this.clientField.getText();
        String projectCode = this.projectCodeField.getText();
        String sowCode = this.sowField.getText();
        float billableRate = Float.parseFloat(this.rateField.getText());

        this.sowModelPresenter.updateCurrentWorkOrder(projectName, client, projectCode, sowCode,
                billableRate);
    }

}
