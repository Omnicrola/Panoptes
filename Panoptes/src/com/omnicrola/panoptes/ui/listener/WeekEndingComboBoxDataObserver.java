package com.omnicrola.panoptes.ui.listener;

import javax.swing.JComboBox;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;

public class WeekEndingComboBoxDataObserver implements IControlObserver {

    private final JComboBox<DateWrapper> weekEndingComboBox;
    private final DateWrapper[] weekEndingDays;
    private final DataController controller;

    public WeekEndingComboBoxDataObserver(DataController controller,
            JComboBox<DateWrapper> weekEndingComboBox, DateWrapper[] weekEndingDays) {
        this.controller = controller;
        this.weekEndingComboBox = weekEndingComboBox;
        this.weekEndingDays = weekEndingDays;
    }

    @Override
    public void currentFilenameChanged(String filename) {
    }

    @Override
    public void dataChanged() {
        DateWrapper weekEnding = this.controller.getWeekEnding();
        for (int i = 0; i < this.weekEndingDays.length; i++) {
            if (this.weekEndingDays[i].equals(weekEnding)) {
                this.weekEndingComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public void timeblockSetChanged(TimeblockSet timeblockSet) {
    }

}
