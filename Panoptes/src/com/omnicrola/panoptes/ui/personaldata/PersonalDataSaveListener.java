package com.omnicrola.panoptes.ui.personaldata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.PersonalData;

public class PersonalDataSaveListener implements ActionListener {

    private final DataController controller;
    private final JTextField firstNameField;
    private final JTextField addressField;
    private final JTextField cityField;
    private final JTextField zipField;
    private final JTextField phoneField;
    private final JTextField emailField;
    private final JTextField lastNameField;
    private final JTextField stateField;

    public PersonalDataSaveListener(DataController controller, JTextField firstName,
            JTextField lastName, JTextField address, JTextField city, JTextField state,
            JTextField zip, JTextField phone, JTextField email) {
        this.controller = controller;
        this.firstNameField = firstName;
        this.lastNameField = lastName;
        this.addressField = address;
        this.cityField = city;
        this.stateField = state;
        this.zipField = zip;
        this.phoneField = phone;
        this.emailField = email;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        PersonalData personalData = new PersonalData();
        personalData.setFirstName(this.firstNameField.getText());
        personalData.setLastName(this.lastNameField.getText());

        personalData.setAddress(this.addressField.getText());
        personalData.setCity(this.cityField.getText());
        personalData.setState(this.stateField.getText());
        personalData.setZip(this.zipField.getText());

        personalData.setPhone(this.phoneField.getText());
        personalData.setEmail(this.emailField.getText());

        this.controller.setPersonalData(personalData);
    }

}
