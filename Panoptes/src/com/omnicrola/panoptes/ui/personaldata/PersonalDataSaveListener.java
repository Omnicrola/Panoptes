package com.omnicrola.panoptes.ui.personaldata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.settings.PersonalData;

public class PersonalDataSaveListener implements ActionListener {

	private final DataController controller;
	private PersonalDataDisplay personalDataDisplay;

	public PersonalDataSaveListener(DataController controller, PersonalDataDisplay personalDataDisplay) {
		this.controller = controller;
		this.personalDataDisplay = personalDataDisplay;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		PersonalData personalData = new PersonalData();
		personalData.setFirstName(getText(PersonalDataDisplay.FIRST_NAME));
		personalData.setLastName(getText(PersonalDataDisplay.LAST_NAME));
		personalData.setCompanyName(getText(PersonalDataDisplay.COMPANY_NAME));

		personalData.setAddress(getText(PersonalDataDisplay.ADDRESS));
		personalData.setCity(getText(PersonalDataDisplay.CITY));
		personalData.setState(getText(PersonalDataDisplay.STATE));
		personalData.setZip(getText(PersonalDataDisplay.ZIP));

		personalData.setPhone(getText(PersonalDataDisplay.PHONE));
		personalData.setEmail(getText(PersonalDataDisplay.EMAIL));

		this.controller.setPersonalData(personalData);
		this.personalDataDisplay.closeDisplay();
	}

	private String getText(String componentName) {
		JTextField textField = this.personalDataDisplay.getNamedComponent(componentName);
		return textField.getText();
	}

}
