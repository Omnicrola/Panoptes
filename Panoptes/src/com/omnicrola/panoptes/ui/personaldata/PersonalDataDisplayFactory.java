package com.omnicrola.panoptes.ui.personaldata;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.IReadPersonalData;
import com.omnicrola.panoptes.ui.CloseDialogListener;

public class PersonalDataDisplayFactory {

	private final DataController controller;

	public PersonalDataDisplayFactory(DataController controller) {
		this.controller = controller;
	}

	public PersonalDataDisplay build() {
		PersonalDataDisplay personalDataDisplay = createDialog();
		populateDialog(personalDataDisplay);
		return personalDataDisplay;
	}

	private void populateDialog(PersonalDataDisplay personalDataDisplay) {
		IReadPersonalData personalData = this.controller.getPersonalData();

		setTextField(personalDataDisplay, personalData.getFirstName(), PersonalDataDisplay.FIRST_NAME);
		setTextField(personalDataDisplay, personalData.getLastName(), PersonalDataDisplay.LAST_NAME);
		setTextField(personalDataDisplay, personalData.getCompanyName(), PersonalDataDisplay.COMPANY_NAME);

		setTextField(personalDataDisplay, personalData.getPhone(), PersonalDataDisplay.PHONE);
		setTextField(personalDataDisplay, personalData.getEmail(), PersonalDataDisplay.EMAIL);

		setTextField(personalDataDisplay, personalData.getStreetAddress(), PersonalDataDisplay.ADDRESS);
		setTextField(personalDataDisplay, personalData.getCity(), PersonalDataDisplay.CITY);
		setTextField(personalDataDisplay, personalData.getState(), PersonalDataDisplay.STATE);
		setTextField(personalDataDisplay, personalData.getZip(), PersonalDataDisplay.ZIP);

	}

	private void setTextField(PersonalDataDisplay personalDataDisplay, String textValue, String componentKey) {
		JTextField firstname = personalDataDisplay.getNamedComponent(componentKey);
		firstname.setText(textValue);
	}

	private PersonalDataDisplay createDialog() {
		PersonalDataDisplay personalDataDisplay = new PersonalDataDisplay();
		personalDataDisplay.setLocationRelativeTo(null);
		JButton saveButton = personalDataDisplay.getNamedComponent(PersonalDataDisplay.BUTTON_SAVE);
		saveButton.addActionListener(new PersonalDataSaveListener(this.controller, personalDataDisplay));

		JButton closeButton = personalDataDisplay.getNamedComponent(PersonalDataDisplay.BUTTON_CLOSE);
		closeButton.addActionListener(new CloseDialogListener(personalDataDisplay));
		return personalDataDisplay;
	}
}
