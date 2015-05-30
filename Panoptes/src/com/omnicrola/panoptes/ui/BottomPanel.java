package com.omnicrola.panoptes.ui;

import javax.swing.JPanel;

public class BottomPanel extends JPanel {

    private static final long serialVersionUID = 6353735405478666497L;

    private final DisplayBlockModelPresenter displayBlockModel;

    public BottomPanel(DisplayBlockModelPresenter displayBlockModel) {
        this.displayBlockModel = displayBlockModel;
    }

    public DisplayBlockModelPresenter getDisplayBlockModelPresenter() {
        return this.displayBlockModel;
    }
}
