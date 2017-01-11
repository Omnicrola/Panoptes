package com.omnicrola.panoptes.ui.listener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.omnicrola.panoptes.data.ProjectGroup;
import com.omnicrola.panoptes.data.WorkStatement;

public class SowTextFieldUpdater implements ISowViewUpdater {

	private final JTextField projectNameField;
	private final JTextField clientField;
	private final JTextField projectCodeField;
	private JComboBox<ProjectGroup> groupSelector;

	public SowTextFieldUpdater(JTextField projectNameField, JTextField clientField, JTextField projectCodeField,
			JComboBox<ProjectGroup> groupSelector) {
		this.projectNameField = projectNameField;
		this.clientField = clientField;
		this.projectCodeField = projectCodeField;
		this.groupSelector = groupSelector;
	}

	@Override
	public void currentSelectionChanged(String projectName, String projectCode, String client, ProjectGroup projectGroup) {
		this.projectNameField.setText(projectName);
		this.clientField.setText(client);
		this.projectCodeField.setText(projectCode);
		this.groupSelector.setSelectedItem(projectGroup);
	}

	@Override
	public void statementAdded(WorkStatement workStatement) {
	}

	@Override
	public void statementRemoved(WorkStatement workStatement) {
	}

}
