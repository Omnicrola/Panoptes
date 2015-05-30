package com.omnicrola.panoptes.ui.autocomplete;

import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.WorkStatement;

public class ProjectOptionProvider implements IOptionProvider {

    private final DataController controller;

    public ProjectOptionProvider(DataController controller) {
        this.controller = controller;
    }

    @Override
    public List<Object> getOptionsList() {
        List<Object> optionList = new ArrayList<>();
        List<WorkStatement> workStatements = this.controller.getWorkStatements();
        for (WorkStatement workStatement : workStatements) {
            optionList.add(workStatement.getProjectName());
        }
        return optionList;
    }

}
