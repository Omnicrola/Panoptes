package com.omnicrola.panoptes.ui.listener;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.omnicrola.panoptes.data.ProjectGroup;
import com.omnicrola.panoptes.data.WorkStatement;

public class SowTextFieldUpdater implements ISowViewUpdater {

	private final JTextField projectNameField;
	private final JTextField clientField;
	private final JTextField sowField;
	private final JTextField projectCodeField;
	private final JFormattedTextField rateField;
	private JComboBox<ProjectGroup> groupSelector;

	public SowTextFieldUpdater(JTextField projectNameField, JTextField clientField, JTextField projectCodeField,
			JTextField sowField, JFormattedTextField rateField, JComboBox<ProjectGroup> groupSelector) {
		this.projectNameField = projectNameField;
		this.clientField = clientField;
		this.projectCodeField = projectCodeField;
		this.sowField = sowField;
		this.rateField = rateField;
		this.groupSelector = groupSelector;
	}

	@Override
	public void currentSelectionChanged(String projectName, String projectCode, String client, String sowCode,
			float billableRate, ProjectGroup projectGroup) {
		this.projectNameField.setText(projectName);
		this.clientField.setText(client);
		this.projectCodeField.setText(projectCode);
		this.sowField.setText(sowCode);
		this.rateField.setText(String.valueOf(billableRate));
		this.groupSelector.setSelectedItem(projectGroup);
	}

	@Override
	public void statementAdded(WorkStatement workStatement) {
	}

	@Override
	public void statementRemoved(WorkStatement workStatement) {
	}

}
