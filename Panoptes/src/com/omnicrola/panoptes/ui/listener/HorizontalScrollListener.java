package com.omnicrola.panoptes.ui.listener;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import com.omnicrola.panoptes.ui.HorizontalAxisPanel;

public class HorizontalScrollListener implements AdjustmentListener {

    private final HorizontalAxisPanel hourAxisPanel;

    public HorizontalScrollListener(HorizontalAxisPanel hourAxisPanel) {
        this.hourAxisPanel = hourAxisPanel;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent event) {
        int value = event.getValue();
        this.hourAxisPanel.setOffsetX(value * -1);
        this.hourAxisPanel.repaint();
    }

}
