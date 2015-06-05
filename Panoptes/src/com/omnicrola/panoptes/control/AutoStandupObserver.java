package com.omnicrola.panoptes.control;

import java.util.Optional;

import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.settings.IReadAppPreferences;
import com.omnicrola.util.ConstructorParameter;

public class AutoStandupObserver implements IControlObserver {

	private static final String STANDUP_CARD_NUMBER = "STU";

	@ConstructorParameter("preferences")
	private IReadAppPreferences appPreferences;
	@ConstructorParameter("dataController")
	private DataController dataController;
	@ConstructorParameter("dayIndex")
	private int dayIndex;
	@ConstructorParameter("blockIndex")
	private int blockIndex;
	private int nextConsecutiveBlockIndex;

	public AutoStandupObserver(IReadAppPreferences appPreferences, DataController dataController, int dayIndex,
			int blockIndex) {

		this.appPreferences = appPreferences;
		this.dataController = dataController;
		this.dayIndex = dayIndex;
		this.blockIndex = blockIndex;
		this.nextConsecutiveBlockIndex = blockIndex + 1;
	}

	@Override
	public void currentFilenameChanged(String filename) {
	}

	@Override
	public void timeblockSetChanged(TimeblockSet updateTimeblockSet) {
		if (this.appPreferences.autoStandup()) {
			Optional<IReadTimeblock> targetBlock = findTargetBlock(updateTimeblockSet);
			if (targetBlock.isPresent()) {
				addStandupBlock(targetBlock);
			}
		}
	}

	private void addStandupBlock(Optional<IReadTimeblock> targetBlock) {
		IReadTimeblock standupBlock = this.dataController.getTimeBlock(this.dayIndex, this.nextConsecutiveBlockIndex);
		if (!standupBlock.isOccupied()) {
			TimeblockSet standupSet = this.dataController.createTimeblockSet(this.dayIndex,
					this.nextConsecutiveBlockIndex, this.nextConsecutiveBlockIndex);
			TimeData standupTimeData = createStandupTimeData(targetBlock.get());
			this.dataController.updateBlockData(standupSet, standupTimeData);
		}
	}

	//@formatter:off
	private Optional<IReadTimeblock> findTargetBlock(TimeblockSet updateTimeblockSet) {
		return updateTimeblockSet.getBlockSet()
				.stream()
				.filter((b) -> b.getDayIndex() == this.dayIndex)
				.filter((b) -> b.getColumnIndex() == this.blockIndex)
				.findFirst();
	}
	//@formatter:on

	@Override
	public void dataChanged() {
	}

	private TimeData createStandupTimeData(IReadTimeblock targetBlock) {
		TimeData timeData = targetBlock.getTimeData();
		TimeData standupData = new TimeData(timeData.getProject(), STANDUP_CARD_NUMBER, timeData.getRole());
		return standupData;
	}

}
