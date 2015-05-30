package com.omnicrola.panoptes.data;

import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.util.ConstructorParameter;

public class MainDataModelFactory {

	@ConstructorParameter("settings")
	private final AppSettings settings;

	public MainDataModelFactory(AppSettings settings) {
		this.settings = settings;
	}

	public MainDataModel build(List<WorkStatement> workStatementList) {
		ArrayList<List<TimeBlock>> timeBlocks = new ArrayList<List<TimeBlock>>();
		for (int i = 0; i < this.settings.getDaysInWeek(); i++) {
			ArrayList<TimeBlock> timeBlockRow = new ArrayList<TimeBlock>();
			for (int j = 0; j < this.settings.getTotalBlocks(); j++) {
				timeBlockRow.add(new TimeBlock(i, j));
			}
			timeBlocks.add(timeBlockRow);
		}
		return new MainDataModel(timeBlocks, workStatementList, this.settings);
	}

}
