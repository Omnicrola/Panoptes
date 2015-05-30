package com.omnicrola.panoptes.ui.listener;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.ui.TopPanelInputSet;

public class ComboBoxUpdateObserver implements IDisplayBlockModelObserver {

    private final TopPanelInputSet topPanelInputSet;

    public ComboBoxUpdateObserver(TopPanelInputSet topPanelInputSet) {
        this.topPanelInputSet = topPanelInputSet;
    }

    @Override
    public void displayChanged() {
    }

    @Override
    public void selectionChanged(TimeblockSet timeblockSet) {
        int rowIndex = timeblockSet.getRowIndex();
        int lowestColumnIndex = timeblockSet.getLowestColumnIndex();
        int highestColumnIndex = timeblockSet.getHighestColumnIndex();

        this.topPanelInputSet.setSelection(rowIndex, lowestColumnIndex, highestColumnIndex);
    }

}
