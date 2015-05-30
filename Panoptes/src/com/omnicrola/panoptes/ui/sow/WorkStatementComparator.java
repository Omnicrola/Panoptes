package com.omnicrola.panoptes.ui.sow;

import java.util.Comparator;

import com.omnicrola.panoptes.data.WorkStatement;

public class WorkStatementComparator implements Comparator<WorkStatement> {

    @Override
    public int compare(WorkStatement first, WorkStatement second) {
        String name1 = first.getProjectName();
        String name2 = second.getProjectName();
        return name1.compareTo(name2);
    }

}
