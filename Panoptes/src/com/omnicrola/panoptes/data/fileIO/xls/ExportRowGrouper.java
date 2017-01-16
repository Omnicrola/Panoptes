package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omnicrola.panoptes.data.ProjectGroup;

public class ExportRowGrouper {

	public Map<ProjectGroup, List<ExportDataRow>> groupRows(List<ExportDataRow> exportList) {
		HashMap<ProjectGroup, List<ExportDataRow>> groupedRows = buildEmptyListsForEachGroup();
		sortIntoGroups(exportList, groupedRows);
		putRowsWithNoHomeIntoInternalProjects(groupedRows);
		return groupedRows;
	}

	private void sortIntoGroups(List<ExportDataRow> exportList, HashMap<ProjectGroup, List<ExportDataRow>> groupedRows) {
		for (ExportDataRow exportDataRow : exportList) {
			ProjectGroup projectGroup = exportDataRow.getWorkStatement().getProjectGroup();
			List<ExportDataRow> group = groupedRows.get(projectGroup);
			group.add(exportDataRow);
		}
	}

	private HashMap<ProjectGroup, List<ExportDataRow>> buildEmptyListsForEachGroup() {
		HashMap<ProjectGroup, List<ExportDataRow>> groupedRows = new HashMap<ProjectGroup, List<ExportDataRow>>();
		for (ProjectGroup projectGroup : ProjectGroup.values()) {
			groupedRows.put(projectGroup, new ArrayList<>());
		}
		return groupedRows;
	}

	private void putRowsWithNoHomeIntoInternalProjects(HashMap<ProjectGroup, List<ExportDataRow>> groupedRows) {
		List<ExportDataRow> rowsWithNoHome = groupedRows.get(ProjectGroup.NONE);
		List<ExportDataRow> internalRows = groupedRows.get(ProjectGroup.INTERNAL_PROJECT);
		internalRows.add(ExportDataRow.EMPTY);
		internalRows.addAll(rowsWithNoHome);
		rowsWithNoHome.clear();
	}

}
