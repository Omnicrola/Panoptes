package com.omnicrola.panoptes.control;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.testing.util.EnhancedTestCase;
import com.omnicrola.testing.util.ObjectWrapper;

public class DataControllerTest extends EnhancedTestCase {

	private AppSettings mockSettings;
	private MainDataModel mockDataModel;
	private DataController dataController;
	private IControlObserver mockControlObserver;

	@Before
	public void setupObjectForTesting() {
		this.mockSettings = useMock(AppSettings.class);
		this.mockDataModel = useMock(MainDataModel.class);
		this.mockControlObserver = useMock(IControlObserver.class);
		this.dataController = new DataController(this.mockDataModel, this.mockSettings);
	}

	@Test
	public void testConstructorParams() throws Exception {
		startReplay();
		assertConstructionParamSame("settings", this.mockSettings, this.dataController);
		assertConstructionParamSame("dataModel", this.mockDataModel, this.dataController);
	}

	@Test
	public void testSetGetCurrentFilename() throws Exception {
		String expectedFilenameReturn = "sasafrass.xmlsmsx";
		String expectedFilenameSet = "johnDoe.blarg";
		expect(this.mockDataModel.getCurrentFilename()).andReturn(expectedFilenameReturn).once();
		this.mockDataModel.setCurrentFilename(expectedFilenameSet);
		expectLastCall().once();
		startReplay();
		this.dataController.setCurrentFilename(expectedFilenameSet);
		assertEquals(expectedFilenameReturn, this.dataController.getCurrentFilename());
	}

	@Test
	public void testSetCurrentFilename() throws Exception {
		String expectedFilename = "sfjfugg.ffd";
		ObjectWrapper<Boolean> observerWasCalled = new ObjectWrapper<Boolean>(false);
		this.mockDataModel.setCurrentFilename(expectedFilename);
		expectLastCall().andAnswer(() -> {
			assertFalse(observerWasCalled.getValue());
			return null;
		});
		this.mockControlObserver.currentFilenameChanged(expectedFilename);
		expectLastCall().andAnswer(() -> {
			observerWasCalled.setValue(true);
			return null;
		});

		startReplay();
		this.dataController.addObserver(this.mockControlObserver);
		this.dataController.setCurrentFilename(expectedFilename);
	}

	@Test
	public void testSetGetPersonalData() throws Exception {
		PersonalData expectedPersonalDataReturn = useMock(PersonalData.class);
		PersonalData expectedPersonalDataSet = useMock(PersonalData.class);
		expect(this.mockDataModel.getPersonalData()).andReturn(expectedPersonalDataReturn).once();
		this.mockDataModel.setPersonalData(expectedPersonalDataSet);
		expectLastCall().once();
		startReplay();
		this.dataController.setPersonalData(expectedPersonalDataSet);
		assertEquals(expectedPersonalDataReturn, this.dataController.getPersonalData());
	}

	@Test
	public void testSetGetWeekEnding() throws Exception {
		DateWrapper expectedDateReturn = useMock(DateWrapper.class);
		DateWrapper expectedDateSet = useMock(DateWrapper.class);
		expect(this.mockDataModel.getWeekEnding()).andReturn(expectedDateReturn).once();
		this.mockDataModel.setWeekEnding(expectedDateSet);
		expectLastCall().once();
		startReplay();
		this.dataController.setWeekEnding(expectedDateSet);
		assertEquals(expectedDateReturn, this.dataController.getWeekEnding());
	}

	@Test
	public void testSetGetWorkStatements() throws Exception {
		List<WorkStatement> expectedWorkStatements = Arrays.asList(useMock(WorkStatement.class),
				useMock(WorkStatement.class), useMock(WorkStatement.class));
		WorkStatement expectedStatement = useMock(WorkStatement.class);
		expect(this.mockDataModel.getWorkStatements()).andReturn(expectedWorkStatements).once();
		this.mockDataModel.addWorkStatement(expectedStatement);
		expectLastCall().once();
		startReplay();
		this.dataController.addWorkStatement(expectedStatement);
		List<WorkStatement> workStatements = this.dataController.getWorkStatements();
		assertNotSame(expectedWorkStatements, workStatements);
		assertCollectionsEqual(expectedWorkStatements, workStatements);
	}

	@Test
	public void testResetTimeblocksClearsModelAndNotifiesObservers() throws Exception {
		ObjectWrapper<Boolean> observerWasCalled = new ObjectWrapper<Boolean>(false);
		this.mockControlObserver.dataChanged();
		expectLastCall().andAnswer(() -> {
			observerWasCalled.setValue(true);
			return null;
		}).once();

		this.mockDataModel.clearAllTimeblocks();
		expectLastCall().andAnswer(() -> {
			assertFalse(observerWasCalled.getValue());
			return null;
		}).once();

		startReplay();
		this.dataController.addObserver(this.mockControlObserver);
		this.dataController.resetAllTimeblocks();
		stopReplay();
	}

	@Test
	public void testGetTimeBlock() throws Exception {
		TimeBlock mockTimeblock = useMock(TimeBlock.class);
		int expectedRow = randI();
		int expectedColumn = randI();
		expect(this.mockDataModel.getBlock(expectedRow, expectedColumn)).andReturn(mockTimeblock).once();
		startReplay();
		assertSame(mockTimeblock, this.dataController.getTimeBlock(expectedRow, expectedColumn));
	}

	@Test
	public void testOverwriteSelectedBlocks() throws Exception {
		int expectedRow = randI();
		int expectedColumn = randI();
		TimeData expectedData = useMock(TimeData.class);
		boolean expectedOccupied = (randI() % 2) == 1;
		TimeBlock timeBlockFromFile = new TimeBlock(expectedRow, expectedColumn);
		TimeBlock timeBlockInMemory = new TimeBlock(expectedRow, expectedColumn);
		timeBlockFromFile.setTimeData(expectedData);
		timeBlockFromFile.setOccupied(expectedOccupied);
		List<TimeBlock> timeblocksFromFile = Arrays.asList(timeBlockFromFile);

		expect(this.mockDataModel.getBlock(expectedRow, expectedColumn)).andReturn(timeBlockInMemory).once();

		startReplay();
		this.dataController.overwriteSelectedBlocks(timeblocksFromFile);
		stopReplay();
		assertSame(expectedData, timeBlockInMemory.getTimeData());
		assertEquals(expectedOccupied, timeBlockInMemory.isOccupied());
	}

	@Test
	public void testOverwriteSelectedBlocks_CallsObserversAfter() throws Exception {
		TimeBlock timeBlock = useNiceMock(TimeBlock.class);
		TimeData mockTimeData = useNiceMock(TimeData.class);
		ObjectWrapper<Boolean> observerWasCalled = new ObjectWrapper<Boolean>(false);
		expect(timeBlock.getTimeData()).andReturn(mockTimeData);

		timeBlock.setTimeData(mockTimeData);
		expectLastCall().andAnswer(() -> {
			assertFalse(observerWasCalled.getValue());
			return null;
		});

		timeBlock.setOccupied(false);
		expectLastCall().andAnswer(() -> {
			assertFalse(observerWasCalled.getValue());
			return null;
		});
		this.mockControlObserver.dataChanged();
		expectLastCall().andAnswer(() -> {
			observerWasCalled.setValue(true);
			return null;
		});

		expect(this.mockDataModel.getBlock(0, 0)).andReturn(timeBlock);

		startReplay();
		this.dataController.addObserver(this.mockControlObserver);
		this.dataController.overwriteSelectedBlocks(Arrays.asList(timeBlock));
	}

}
