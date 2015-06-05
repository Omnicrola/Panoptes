package com.omnicrola.panoptes.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.settings.PersonalData;

public class MainDataModel {

	private final List<List<TimeBlock>> timeblockList;
	private final List<WorkStatement> workStatementList;
	private String currentFile = "";
	private final TimeblockSelectionTracker timeblockSelectionTracker;
	private final AppSettings settings;
	private PersonalData personalData;
	private String exportFilename;
	private DateWrapper weekEnding;

	public MainDataModel(ArrayList<List<TimeBlock>> timeBlocks, List<WorkStatement> workStatementList,
			AppSettings settings) {
		this.timeblockList = timeBlocks;
		this.workStatementList = workStatementList;
		this.settings = settings;
		this.exportFilename = settings.getDefaultExportFilename();
		this.weekEnding = new DateWrapper(new Date());
		this.timeblockSelectionTracker = new TimeblockSelectionTracker();
		this.personalData = new PersonalData();
	}

	public void addWorkStatement(WorkStatement workStatement) {
		this.workStatementList.add(workStatement);
	}

	public void clearAllTimeblocks() {
		for (List<TimeBlock> row : this.timeblockList) {
			for (TimeBlock timeBlock : row) {
				timeBlock.clear();
			}
		}
	}

	public TimeBlock getBlock(int rowIndex, int columnIndex) {
		int maxRows = this.settings.getDaysInWeek();
		int maxColumns = this.settings.getTotalBlocks();
		if (rowIndex >= maxRows || columnIndex >= maxColumns) {
			return TimeBlock.EMPTY;
		}
		return this.timeblockList.get(rowIndex).get(columnIndex);
	}

	public String getCurrentFilename() {
		return this.currentFile;
	}

	public String getExportFilename() {
		return this.exportFilename;
	}

	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public List<List<TimeBlock>> getTimeBlocks() {
		return this.timeblockList;
	}

	public TimeblockSelectionTracker getTimeblockSelectionTracker() {
		return this.timeblockSelectionTracker;
	}

	public DateWrapper getWeekEnding() {
		return this.weekEnding;
	}

	public List<WorkStatement> getWorkStatements() {
		return this.workStatementList;
	}

	public void setCurrentFilename(String filename) {
		this.currentFile = filename;
	}

	public void setExportFilename(String exportFilename) {
		this.exportFilename = exportFilename;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public void setWeekEnding(DateWrapper weekEnding) {
		this.weekEnding = weekEnding;
	}

}
