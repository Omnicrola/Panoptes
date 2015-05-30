package com.omnicrola.panoptes.ui.sow;

import java.util.List;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.WorkStatement;

public class WorkStatementController {

    private final DataController controller;

    public WorkStatementController(DataController controller) {
        this.controller = controller;
    }

    public List<WorkStatement> getWorkStatements() {
        return this.controller.getWorkStatements();
    }

}
