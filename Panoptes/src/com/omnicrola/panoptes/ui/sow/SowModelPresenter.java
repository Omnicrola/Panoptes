package com.omnicrola.panoptes.ui.sow;

import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.ProjectGroup;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.ui.listener.ISowViewUpdater;

public class SowModelPresenter {

	public static final WorkStatement NEW = new WorkStatement("<NEW>", "", "", "", 0, ProjectGroup.CLIENT_BILLABLE);

	private WorkStatement currentWorkStatement;
	private final List<ISowViewUpdater> viewList;
	private final DataController controller;

	public SowModelPresenter(DataController controller) {
		this.controller = controller;
		this.viewList = new ArrayList<>();
		this.currentWorkStatement = WorkStatement.EMPTY;
	}

	public void addView(ISowViewUpdater viewUpdater) {
		this.viewList.add(viewUpdater);
	}

	public List<WorkStatement> getAllWorkStatements() {
		List<WorkStatement> workStatements = this.controller.getWorkStatements();
		workStatements.add(0, NEW);
		return workStatements;
	}

	private void notifyViewsOfNewStatement(WorkStatement workStatement) {
		for (ISowViewUpdater updater : this.viewList) {
			updater.statementAdded(workStatement);
		}
	}

	public void removeView(ISowViewUpdater viewUpdater) {
		this.viewList.remove(viewUpdater);
	}

	public void setCurrentWorkStatement(WorkStatement workStatement) {
		this.currentWorkStatement = workStatement;
		String client = this.currentWorkStatement.getClient();
		String projectCode = this.currentWorkStatement.getProjectCode();
		String projectName = this.currentWorkStatement.getProjectName();
		String sowCode = this.currentWorkStatement.getSowCode();
		float billableRate = this.currentWorkStatement.getBillableRate();
		ProjectGroup group = this.currentWorkStatement.getProjectGroup();

		for (ISowViewUpdater updater : this.viewList) {
			updater.currentSelectionChanged(projectName, projectCode, client, sowCode, billableRate, group);
		}
	}

	public void updateCurrentWorkOrder(String projectName, String client, String projectCode, String sowCode,
			float billableRate, ProjectGroup projectGroup) {
		if (this.currentWorkStatement == NEW) {
			WorkStatement workStatement = new WorkStatement(projectName, client, projectCode, sowCode, billableRate,
					projectGroup);
			this.controller.addWorkStatement(workStatement);
			notifyViewsOfNewStatement(workStatement);
			setCurrentWorkStatement(NEW);
		} else {
			this.currentWorkStatement.setData(projectName, client, projectCode, sowCode, billableRate, projectGroup);
		}
	}

}
