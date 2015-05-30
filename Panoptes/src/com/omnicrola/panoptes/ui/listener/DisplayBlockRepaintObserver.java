package com.omnicrola.panoptes.ui.listener;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.ui.BlockPanel;

public class DisplayBlockRepaintObserver implements IDisplayBlockModelObserver {

    private final BlockPanel displayBlockPanel;

    public DisplayBlockRepaintObserver(BlockPanel displayBlockPanel) {
        this.displayBlockPanel = displayBlockPanel;
    }

    @Override
    public void displayChanged() {
        this.displayBlockPanel.repaint();
    }

    @Override
    public void selectionChanged(TimeblockSet timeblockSet) {
        this.displayBlockPanel.repaint();
    }

}
