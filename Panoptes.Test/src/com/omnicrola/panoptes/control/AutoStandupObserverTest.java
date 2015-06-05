package com.omnicrola.panoptes.control;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class AutoStandupObserverTest extends EnhancedTestCase {

	private DataController mockDataController;
	private TimeData timeDataBeforeStandup;
	private IReadTimeblock mockTimeblockOnStandup;
	private int expectedDay;
	private int expectedTimeToWatch;
	private int expectedTimeToAddStandup;

	@Before
	public void initializeMocks() {
		this.expectedDay = 5;// randI();
		this.expectedTimeToWatch = 100;// randI();
		this.expectedTimeToAddStandup = this.expectedTimeToWatch + 1;

		this.mockDataController = useMock(DataController.class);
		this.mockTimeblockOnStandup = useMock(IReadTimeblock.class);

		this.timeDataBeforeStandup = new TimeData("my project", "not standup", "leading dog");
	}

	@Test
	public void testImplementsInterface() throws Exception {
		assertImplementsInterface(IControlObserver.class, AutoStandupObserver.class);
	}

	@Test
	public void testConstructorParam() throws Exception {
		DataController mockDataController = useMock(DataController.class);
		startReplay();
		int expectedDayIndex = randI();
		int expectedBlockIndex = randI();
		AutoStandupObserver autoStandupObserver = new AutoStandupObserver(mockDataController, expectedDayIndex,
				expectedBlockIndex);
		assertConstructionParamSame("dataController", mockDataController, autoStandupObserver);
		assertConstructionParameterEquals("dayIndex", expectedDayIndex, autoStandupObserver);
		assertConstructionParameterEquals("blockIndex", expectedBlockIndex, autoStandupObserver);
	}

	@Test
	public void testTimeblockSetChanged_AddsTimeblockForStandup() throws Exception {
		TimeblockSet mockTimeblockSet = createTimeblockSetWithTargetBlock();
		setStandupBlockOccupiedState(false);

		expect(
				this.mockDataController.createTimeblockSet(this.expectedDay, this.expectedTimeToAddStandup,
						this.expectedTimeToAddStandup)).andReturn(mockTimeblockSet);

		Capture<TimeData> timedataCapture = Capture.newInstance();
		this.mockDataController.updateBlockData(eq(mockTimeblockSet), capture(timedataCapture));

		startReplay();
		AutoStandupObserver autoStandupObserver = new AutoStandupObserver(this.mockDataController, this.expectedDay,
				this.expectedTimeToWatch);
		autoStandupObserver.timeblockSetChanged(mockTimeblockSet);
		stopReplay();
		TimeData actualTimeData = timedataCapture.getValue();
		assertEquals(this.timeDataBeforeStandup.getProject(), actualTimeData.getProject());
		assertEquals("STU", actualTimeData.getCard());
		assertEquals(this.timeDataBeforeStandup.getRole(), actualTimeData.getRole());
	}

	@Test
	public void testTimeblockSetChanged_DoesNothingIfNextBlockIsOccupied() throws Exception {
		TimeblockSet mockTimeblockSet = createTimeblockSetWithTargetBlock();
		setStandupBlockOccupiedState(true);

		startReplay();
		AutoStandupObserver autoStandupObserver = new AutoStandupObserver(this.mockDataController, this.expectedDay,
				this.expectedTimeToWatch);
		autoStandupObserver.timeblockSetChanged(mockTimeblockSet);
		stopReplay();
	}

	@Test
	public void testTimeblockSetChanged_DoesNothingIfSetDoesNotBelongToTargetDay() throws Exception {
		TimeblockSet mockTimeblockSet = useMock(TimeblockSet.class);
		expect(mockTimeblockSet.getBlockSet()).andReturn(new ArrayList<>());

		startReplay();
		AutoStandupObserver autoStandupObserver = new AutoStandupObserver(this.mockDataController, this.expectedDay,
				this.expectedTimeToWatch);
		autoStandupObserver.timeblockSetChanged(mockTimeblockSet);
		stopReplay();
	}

	private void setStandupBlockOccupiedState(boolean isOccupied) {
		expect(this.mockDataController.getTimeBlock(this.expectedDay, this.expectedTimeToAddStandup)).andReturn(
				this.mockTimeblockOnStandup).anyTimes();
		expect(this.mockTimeblockOnStandup.isOccupied()).andReturn(isOccupied).anyTimes();
	}

	private TimeblockSet createTimeblockSetWithTargetBlock() {
		TimeblockSet mockTimeblockSet = useMock(TimeblockSet.class);
		TimeBlock timeblockBeforeStandup = new TimeBlock(this.expectedDay, this.expectedTimeToWatch);
		timeblockBeforeStandup.setOccupied(true);
		timeblockBeforeStandup.setTimeData(this.timeDataBeforeStandup);
		expect(mockTimeblockSet.getBlockSet()).andReturn(Arrays.asList(timeblockBeforeStandup));
		return mockTimeblockSet;
	}
}
