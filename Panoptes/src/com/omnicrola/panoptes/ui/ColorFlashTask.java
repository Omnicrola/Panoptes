package com.omnicrola.panoptes.ui;

import java.util.TimerTask;

import com.omnicrola.panoptes.data.DisplayBlock;
import com.omnicrola.panoptes.ui.DisplayBlockModelPresenter.DisplayBlockObserverProxy;

public class ColorFlashTask extends TimerTask {

    private final DisplayBlock displayBlock;
    private final boolean flashState;
    private final DisplayBlockObserverProxy observerProxy;

    public ColorFlashTask(DisplayBlock displayBlock, DisplayBlockObserverProxy observerProxy,
            boolean flashState) {
        this.displayBlock = displayBlock;
        this.observerProxy = observerProxy;
        this.flashState = flashState;
    }

    @Override
    public void run() {
        this.displayBlock.setFlashing(this.flashState);
        this.observerProxy.notifyDisplayBlockObservers();
    }

}
