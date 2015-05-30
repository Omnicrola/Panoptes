package com.omnicrola.panoptes.ui.personaldata;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.IReadPersonalData;
import com.omnicrola.panoptes.ui.DialogFactoryToolbox;
import com.omnicrola.panoptes.ui.listener.CloseDialogListener;

public class PersonalDataDisplayFactory {

    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);

    private final DataController controller;
    private final DialogFactoryToolbox toolbox;

    public PersonalDataDisplayFactory(DataController controller, DialogFactoryToolbox toolbox) {
        this.controller = controller;
        this.toolbox = toolbox;
    }

    private PersonalDataSaveListener addFields(JPanel rightPanel) {
        IReadPersonalData personalData = this.controller.getPersonalData();
        int width = 20;
        JTextField firstName = this.toolbox.makeField(width, personalData.getFirstName());
        JTextField lastName = this.toolbox.makeField(width, personalData.getLastName());

        JTextField address = this.toolbox.makeField(width, personalData.getAddress());
        JTextField city = this.toolbox.makeField(width, personalData.getCity());
        JTextField state = this.toolbox.makeField(width, personalData.getState());
        JTextField zip = this.toolbox.makeField(width, personalData.getZip());

        JTextField phone = this.toolbox.makeField(width, personalData.getPhone());
        JTextField email = this.toolbox.makeField(width, personalData.getEmail());

        rightPanel.add(firstName);
        rightPanel.add(lastName);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(address);
        rightPanel.add(city);
        rightPanel.add(state);
        rightPanel.add(zip);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(phone);
        rightPanel.add(email);

        return new PersonalDataSaveListener(this.controller, firstName, lastName, address, city,
                state, zip, phone, email);
    }

    private void addLabels(JPanel leftPanel) {
        leftPanel.add(this.toolbox.makeLabel("First Name:", LABEL_FONT));
        leftPanel.add(this.toolbox.makeLabel("Last Name:", LABEL_FONT));
        leftPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(this.toolbox.makeLabel("Address:", LABEL_FONT));
        leftPanel.add(this.toolbox.makeLabel("City:", LABEL_FONT));
        leftPanel.add(this.toolbox.makeLabel("State:", LABEL_FONT));
        leftPanel.add(this.toolbox.makeLabel("ZIP:", LABEL_FONT));
        leftPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(this.toolbox.makeLabel("Phone:", LABEL_FONT));
        leftPanel.add(this.toolbox.makeLabel("Email:", LABEL_FONT));
    }

    public PersonalDataDisplay build() {
        PersonalDataDisplay personalDataDisplay = new PersonalDataDisplay();
        personalDataDisplay.setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        addLabels(leftPanel);
        PersonalDataSaveListener saveListener = addFields(rightPanel);

        JPanel buttonPanel = buildButtons(saveListener, personalDataDisplay);

        Container contentPane = personalDataDisplay.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new FlowLayout());
        editorPanel.add(leftPanel);
        editorPanel.add(rightPanel);

        contentPane.add(editorPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);

        return personalDataDisplay;
    }

    private JPanel buildButtons(PersonalDataSaveListener saveListener,
            PersonalDataDisplay personalDataDisplay) {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(saveListener);
        saveButton.setAlignmentX(SwingConstants.RIGHT);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseDialogListener(personalDataDisplay));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        buttonPanel.add(saveButton);
        return buttonPanel;
    }
}
