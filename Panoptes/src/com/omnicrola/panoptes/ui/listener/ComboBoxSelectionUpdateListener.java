package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.ui.DisplayBlockModelPresenter;
import com.omnicrola.panoptes.ui.TopPanelInputSet;

public class ComboBoxSelectionUpdateListener implements ActionListener {

    private final TopPanelInputSet topPanelInputSet;
    private final DisplayBlockModelPresenter displayModelPresenter;
    private final DataController dataController;
    private boolean enabled;

    public ComboBoxSelectionUpdateListener(DisplayBlockModelPresenter displayModelPresenter,
            DataController dataController, TopPanelInputSet topPanelInputSet) {
        this.displayModelPresenter = displayModelPresenter;
        this.dataController = dataController;
        this.topPanelInputSet = topPanelInputSet;
        this.enabled = true;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (this.enabled) {
            int selectedDay = this.topPanelInputSet.getSelectedDay();
            int startTimeIndex = this.topPanelInputSet.getStartTimeIndex();
            int endTimeIndex = this.topPanelInputSet.getEndTimeIndex();

            TimeblockSet selection = this.dataController.createTimeblockSet(selectedDay,
                    startTimeIndex, endTimeIndex);

            this.enabled = false;
            this.displayModelPresenter.setSelection(selection);
            this.enabled = true;
        }
    }

}
