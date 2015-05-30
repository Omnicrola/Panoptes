package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.Comparator;

public class ExportRowComparator implements Comparator<ExportDataRow> {

    @Override
    public int compare(ExportDataRow row1, ExportDataRow row2) {
        String project1 = row1.getWorkStatement().getProjectName();
        String project2 = row2.getWorkStatement().getProjectName();
        int projectCompare = project1.compareTo(project2);
        if (projectCompare == 0) {
            String role1 = row1.getRole();
            String role2 = row2.getRole();
            int roleCompare = role1.compareTo(role2);
            if (roleCompare == 0) {
                String card1 = row1.getCard();
                String card2 = row2.getCard();
                return card1.compareTo(card2);
            } else {
                return roleCompare;
            }
        } else {
            return projectCompare;
        }
    }
}
