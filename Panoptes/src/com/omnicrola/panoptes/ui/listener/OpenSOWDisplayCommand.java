package com.omnicrola.panoptes.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.panoptes.ui.sow.SowDialog;
import com.omnicrola.panoptes.ui.sow.SowDialogFactory;
import com.omnicrola.panoptes.ui.sow.SowModelPresenter;

public class OpenSOWDisplayCommand implements ActionListener {

    private final SowDialogFactory dialogBuilder;
    private final SowModelPresenter sowModelPresenter;

    public OpenSOWDisplayCommand(SowModelPresenter sowModelPresenter, SowDialogFactory dialogBuilder) {
        this.sowModelPresenter = sowModelPresenter;
        this.dialogBuilder = dialogBuilder;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        SowDialog dialog = this.dialogBuilder.build(this.sowModelPresenter);
        dialog.setVisible(true);

    }

}
