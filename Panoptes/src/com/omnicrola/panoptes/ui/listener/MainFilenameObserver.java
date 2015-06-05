package com.omnicrola.panoptes.ui.listener;

import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.settings.AppSettings;

public class MainFilenameObserver implements IControlObserver {

    private final MainFrame mainFrame;
    private final AppSettings settings;

    public MainFilenameObserver(MainFrame mainFrame, AppSettings settings) {
        this.mainFrame = mainFrame;
        this.settings = settings;
    }

    @Override
    public void currentFilenameChanged(String filename) {
        this.mainFrame.setTitle(this.settings.getApplicationTitle() + " : " + filename);
    }

    @Override
    public void dataChanged() {
    }

    @Override
    public void timeblockSetChanged(TimeblockSet timeblockSet) {
    }
}
