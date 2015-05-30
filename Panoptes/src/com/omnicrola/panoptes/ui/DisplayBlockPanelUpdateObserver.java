package com.omnicrola.panoptes.ui;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.control.TimeblockSet;

public class DisplayBlockPanelUpdateObserver implements IControlObserver {

    private final DisplayBlockModelPresenter displayModelPresenter;
    private final DataController controller;

    public DisplayBlockPanelUpdateObserver(DisplayBlockModelPresenter displayModelPresenter,
            DataController controller) {
        this.displayModelPresenter = displayModelPresenter;
        this.controller = controller;
    }

    @Override
    public void currentFilenameChanged(String filename) {
    }

    @Override
    public void dataChanged() {
        TimeblockSet allBlocks = this.controller.getAllTimeblocks();
        this.displayModelPresenter.refreshBlocks(allBlocks);
    }

    @Override
    public void timeblockSetChanged(TimeblockSet timeblockSet) {
        this.displayModelPresenter.refreshBlocks(timeblockSet);

    }

}
