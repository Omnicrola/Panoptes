package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.omnicrola.panoptes.data.ProjectGroup;

public class ExportRowGrouper {

	public Map<ProjectGroup, List<ExportDataRow>> groupRows(List<ExportDataRow> exportList) {
		HashMap<ProjectGroup, List<ExportDataRow>> groupedRows = buildEmptyListsForEachGroup();
		sortIntoGroups(exportList, groupedRows);
		putRowsWithNoHomeIntoInternalProjects(groupedRows);
		insertBlankRows(groupedRows);
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

	private void insertBlankRows(HashMap<ProjectGroup, List<ExportDataRow>> groupedRows) {
		for (Entry<ProjectGroup, List<ExportDataRow>> entry : groupedRows.entrySet()) {
			insertBlankRows(entry.getValue());
		}
	}

	private void insertBlankRows(List<ExportDataRow> dataList) {
		List<Integer> indexesWhereProjectsChange = new ArrayList<>();

		if (!dataList.isEmpty()) {
			String lastProject = dataList.get(0).getWorkStatement().getProjectName();

			for (ExportDataRow exportDataRow : dataList) {
				String projectName = exportDataRow.getWorkStatement().getProjectName();
				if (!projectName.equals(lastProject)) {
					lastProject = projectName;
					indexesWhereProjectsChange.add(dataList.indexOf(exportDataRow));
				}
			}

			addBlankRowFromBottomUp(dataList, indexesWhereProjectsChange);
		}
	}

	private void addBlankRowFromBottomUp(List<ExportDataRow> dataList, List<Integer> indexesWhereProjectsChange) {
		int arrayEnd = indexesWhereProjectsChange.size() - 1;
		for (int i = arrayEnd; i >= 0; i--) {
			Integer index = indexesWhereProjectsChange.get(i);
			dataList.add(index, ExportDataRow.EMPTY);
		}
	}

}
