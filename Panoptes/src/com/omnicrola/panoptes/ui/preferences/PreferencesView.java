package com.omnicrola.panoptes.ui.preferences;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

@SuppressWarnings("serial")
public class PreferencesView extends JDialog implements IPreferencesView {

	private JCheckBox autoStandupCheckbox;
	private JButton saveButton;
	private JButton cancelButton;

	public PreferencesView() {
		this.setSize(300, 300);
		this.setTitle("Preferences");
		this.setModal(true);
		this.getContentPane().setLayout(new FlowLayout());
		addAutoStandupCheckbox();
		this.saveButton = addButton("Save", "button-save");
		this.cancelButton = addButton("Cancel", "button-cancel");
	}

	private JButton addButton(String text, String name) {
		JButton button = new JButton(text);
		button.setName(name);
		this.getContentPane().add(button);
		return button;
	}

	private void addAutoStandupCheckbox() {
		this.autoStandupCheckbox = new JCheckBox();
		this.autoStandupCheckbox.setName("auto-standup-checkbox");
		this.autoStandupCheckbox.setText("Automatic Standup");
		this.autoStandupCheckbox
				.setToolTipText("When enabled: automatically creates an entry for Standup (STU) at 10:00 when you create an entry at 9:45");
		this.getContentPane().add(this.autoStandupCheckbox);
	}

	@Override
	public void setAutoStandup(boolean isSelected) {
		this.autoStandupCheckbox.setSelected(isSelected);
	}

	@Override
	public boolean getAutoStandup() {
		return this.autoStandupCheckbox.isSelected();
	}

	@Override
	public void addSaveListener(ActionListener actionListener) {
		this.saveButton.addActionListener(actionListener);
	}

	@Override
	public void addCancelListener(ActionListener actionListener) {
		this.cancelButton.addActionListener(actionListener);
	}

	@Override
	public void showDisplay() {
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void closeDisplay() {
		this.setVisible(false);
	}

}
