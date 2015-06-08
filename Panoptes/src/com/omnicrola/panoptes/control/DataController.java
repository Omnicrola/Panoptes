package com.omnicrola.panoptes.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.control.TimeblockIndexIterator.IndexTuple;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.IReadPersonalData;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.data.fileIO.IFileWriter;
import com.omnicrola.panoptes.data.fileIO.PanoptesException;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.settings.IReadAppPreferences;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.util.ConstructorParameter;

public class DataController {

	@ConstructorParameter("settings")
	private final AppSettings settings;
	@ConstructorParameter("dataModel")
	private final MainDataModel mainDataModel;
	private final List<IControlObserver> observerList;

	public DataController(MainDataModel mainDataModel, AppSettings settings) {
		this.mainDataModel = mainDataModel;
		this.settings = settings;
		this.observerList = new ArrayList<>();
	}

	public void addObserver(IControlObserver observer) {
		this.observerList.add(observer);
	}

	public TimeblockSet createTimeblockSet(int rowIndex, int startColumn, int endColumn) {
		return createTimeblockSet(rowIndex, rowIndex, startColumn, endColumn);
	}

	public TimeblockSet createTimeblockSet(int startRow, int endRow, int startColumn, int endColumn) {
		if (startColumn > endColumn) {
			int temp = startColumn;
			startColumn = endColumn;
			endColumn = temp;
		}
		if (startRow > endRow) {
			int temp = startRow;
			startRow = endRow;
			endRow = temp;
		}

		List<IReadTimeblock> selectedList = new ArrayList<>();
		List<List<TimeBlock>> timeBlocks = this.mainDataModel.getTimeBlocks();
		for (int i = startRow; i <= endRow; i++) {
			List<TimeBlock> row = timeBlocks.get(i);
			for (int j = startColumn; j <= endColumn; j++) {
				selectedList.add(row.get(j));
			}
		}
		return new TimeblockSet(selectedList);

	}

	public TimeblockSet getAllTimeblocks() {
		return createAllBlockSet();
	}

	public String getCurrentFilename() {
		return this.mainDataModel.getCurrentFilename();
	}

	public String getExportFilename() {
		return this.mainDataModel.getExportFilename();
	}

	public IReadPersonalData getPersonalData() {
		return this.mainDataModel.getPersonalData();
	}

	public IReadTimeblock getTimeBlock(int rowIndex, int columnIndex) {
		return this.mainDataModel.getBlock(rowIndex, columnIndex);
	}

	public DateWrapper getWeekEnding() {
		return this.mainDataModel.getWeekEnding();
	}

	public void setWeekEnding(DateWrapper weekEnding) {
		this.mainDataModel.setWeekEnding(weekEnding);
		notifyObserversDataChanged();
	}

	public void addWorkStatement(WorkStatement workStatement) {
		this.mainDataModel.addWorkStatement(workStatement);
	}

	public List<WorkStatement> getWorkStatements() {
		return new ArrayList<WorkStatement>(this.mainDataModel.getWorkStatements());
	}

	public void overwriteSelectedBlocks(List<TimeBlock> blockListFromFile) {
		for (IReadTimeblock blockFromFile : blockListFromFile) {
			int dayIndex = blockFromFile.getDayIndex();
			int blockIndex = blockFromFile.getColumnIndex();
			TimeBlock block = this.mainDataModel.getBlock(dayIndex, blockIndex);
			block.setTimeData(blockFromFile.getTimeData());
			block.setOccupied(blockFromFile.isOccupied());
		}
		notifyObserversDataChanged();
	}

	public void resetAllTimeblocks() {
		this.mainDataModel.clearAllTimeblocks();
		notifyObserversDataChanged();
	}

	public void setCurrentFilename(String filename) {
		this.mainDataModel.setCurrentFilename(filename);
		for (IControlObserver observer : this.observerList) {
			observer.currentFilenameChanged(filename);
		}
	}

	public void setExportFilename(String exportFilename) {
		this.mainDataModel.setExportFilename(exportFilename);
	}

	public void setPersonalData(PersonalData personalData) {
		this.mainDataModel.setPersonalData(personalData);
	}

	public void updateBlockData(TimeblockSet timeblockSet, TimeData timeData) {
		TimeblockIndexIterator indexIterator = timeblockSet.indexIterator();
		while (indexIterator.hasNext()) {
			IndexTuple next = indexIterator.next();
			TimeBlock block = this.mainDataModel.getBlock(next.getRow(), next.getColumn());
			block.setTimeData(timeData);
			block.setParentSelection(timeblockSet);
			block.setOccupied(true);
		}
		notifyObserversDataSetChanged(timeblockSet);
	}

	public void writeDataToFile(File selectedFile, IFileWriter dataWriter) throws PanoptesException {
		TimeblockSet allTimeblocks = getAllTimeblocks();
		dataWriter.writeDataToFile(selectedFile, this.mainDataModel.getWeekEnding(), allTimeblocks);
	}

	private TimeblockSet createAllBlockSet() {
		int rows = this.settings.getDaysInWeek() - 1;
		int columns = this.settings.getTotalBlocks() - 1;
		return createTimeblockSet(0, rows, 0, columns);
	}

	private void notifyObserversDataChanged() {
		for (IControlObserver observer : this.observerList) {
			observer.dataChanged();
		}
	}

	private void notifyObserversDataSetChanged(TimeblockSet timeblockSet) {
		for (IControlObserver observer : this.observerList) {
			observer.timeblockSetChanged(timeblockSet);
		}
	}

	public IReadAppPreferences getPreferences() {
		return this.mainDataModel.getPreferences();
	}

	public void setPreferences(AppPreferences preferencesToSave) {
		this.mainDataModel.setPreferences(preferencesToSave);
	}

}
