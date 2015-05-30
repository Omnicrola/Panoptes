package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.ui.TopPanelInputSet;

public class AddTimeblockListener implements ActionListener {

    private final DataController mainController;
    private final TopPanelInputSet topPanelInputSet;

    public AddTimeblockListener(DataController controller, TopPanelInputSet topPanelInputSet) {
        this.mainController = controller;
        this.topPanelInputSet = topPanelInputSet;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int selectedDay = this.topPanelInputSet.getSelectedDay();
        int startBlockIndex = this.topPanelInputSet.getEndTimeIndex();
        int endBlockIndex = this.topPanelInputSet.getStartTimeIndex();
        String project = this.topPanelInputSet.getProject();
        String card = this.topPanelInputSet.getCard();
        String role = this.topPanelInputSet.getRole();

        TimeData timeData = new TimeData(project, card, role);
        TimeblockSet timeblockSet = this.mainController.createTimeblockSet(selectedDay,
                startBlockIndex, endBlockIndex);
        this.mainController.updateBlockData(timeblockSet, timeData);

    }
}
