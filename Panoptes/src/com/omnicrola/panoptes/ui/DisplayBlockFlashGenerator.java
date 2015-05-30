package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.util.Timer;

import com.omnicrola.panoptes.data.DisplayBlock;
import com.omnicrola.panoptes.ui.DisplayBlockModelPresenter.DisplayBlockObserverProxy;

public class DisplayBlockFlashGenerator {

    private final Timer timer;

    public DisplayBlockFlashGenerator() {
        this.timer = new Timer();
    }

    public void checkForColorChange(DisplayBlock displayBlock, Color newColor,
            DisplayBlockObserverProxy observerProxy) {
        this.timer.schedule(new ColorFlashTask(displayBlock, observerProxy, true), 0);
        this.timer.schedule(new ColorFlashTask(displayBlock, observerProxy, false), 100);
        this.timer.schedule(new ColorFlashTask(displayBlock, observerProxy, true), 200);
        this.timer.schedule(new ColorFlashTask(displayBlock, observerProxy, false), 300);

    }
}
