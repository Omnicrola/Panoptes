package com.omnicrola.panoptes.ui.listener;

import com.omnicrola.panoptes.control.TimeblockSet;

public interface IDisplayBlockModelObserver {

    public void displayChanged();

    public void selectionChanged(TimeblockSet timeblockSet);
}
