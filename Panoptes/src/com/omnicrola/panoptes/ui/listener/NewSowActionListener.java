package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.ui.sow.SowModelPresenter;

public class NewSowActionListener implements ActionListener {

    private final SowModelPresenter sowModelPresenter;

    public NewSowActionListener(SowModelPresenter sowModelPresenter) {
        this.sowModelPresenter = sowModelPresenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.sowModelPresenter.setCurrentWorkStatement(WorkStatement.EMPTY);
    }

}
