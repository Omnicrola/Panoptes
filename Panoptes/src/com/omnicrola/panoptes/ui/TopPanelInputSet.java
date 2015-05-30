package com.omnicrola.panoptes.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;

public class TopPanelInputSet {

    private class ComboBoxSelectionUpdater implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            updateDisplayModel();
        }

    }

    private final JComboBox<String> dayComboBox;
    private final JComboBox<String> startComboBox;
    private final JComboBox<String> endComboBox;
    private final JTextField projectField;
    private final JTextField cardField;
    private final JComboBox<String> roleField;
    private boolean responsive;
    private final DisplayBlockModelPresenter displayModelPresenter;
    private final DataController controller;
    private final JComboBox<DateWrapper> weekEndingComboBox;

    public TopPanelInputSet(DisplayBlockModelPresenter displayModelPresenter,
            DataController controller, JComboBox<DateWrapper> weekEndingComboBox,
            JComboBox<String> dayComboBox, JComboBox<String> startComboBox,
            JComboBox<String> endComboBox, JTextField projectField, JTextField cardField,
            JComboBox<String> roleField) {
        this.displayModelPresenter = displayModelPresenter;
        this.controller = controller;
        this.weekEndingComboBox = weekEndingComboBox;

        ComboBoxSelectionUpdater comboBoxSelectionUpdater = new ComboBoxSelectionUpdater();
        this.dayComboBox = dayComboBox;
        this.startComboBox = startComboBox;
        this.endComboBox = endComboBox;
        this.weekEndingComboBox.addActionListener(comboBoxSelectionUpdater);
        this.dayComboBox.addActionListener(comboBoxSelectionUpdater);
        this.startComboBox.addActionListener(comboBoxSelectionUpdater);
        this.endComboBox.addActionListener(comboBoxSelectionUpdater);

        this.projectField = projectField;
        this.cardField = cardField;
        this.roleField = roleField;
        this.responsive = true;
    }

    public String getCard() {
        return this.cardField.getText();
    }

    public int getEndTimeIndex() {
        return this.endComboBox.getSelectedIndex() - 1;
    }

    public String getProject() {
        return this.projectField.getText();
    }

    public String getRole() {
        return this.roleField.getSelectedItem().toString();
    }

    public int getSelectedDay() {
        return this.dayComboBox.getSelectedIndex();
    }

    public int getStartTimeIndex() {
        return this.startComboBox.getSelectedIndex();
    }

    public JComboBox<DateWrapper> getWeekEndingComboBox() {
        return this.weekEndingComboBox;
    }

    public void setSelection(int dayIndex, int startIndex, int endIndex) {
        if (this.responsive) {
            this.responsive = false;
            this.dayComboBox.setSelectedIndex(dayIndex);
            this.startComboBox.setSelectedIndex(startIndex);
            this.endComboBox.setSelectedIndex(endIndex + 1);
            this.responsive = true;
        }
    }

    private void updateDisplayModel() {
        if (this.responsive) {
            this.responsive = false;
            TimeblockSet selection = this.controller.createTimeblockSet(getSelectedDay(),
                    getStartTimeIndex(), getEndTimeIndex());
            this.displayModelPresenter.setSelection(selection);
            this.controller.setWeekEnding((DateWrapper) this.weekEndingComboBox.getSelectedItem());
            this.responsive = true;
        }
    }
}
