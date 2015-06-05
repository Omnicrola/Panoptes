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
import com.omnicrola.panoptes.settings.IReadAppPreferences;
import com.omnicrola.testing.util.EnhancedTestCase;

public class AutoStandupObserverTest extends EnhancedTestCase {

	private DataController mockDataController;
	private TimeData timeDataBeforeStandup;
	private IReadTimeblock mockTimeblockOnStandup;
	private int expectedDay;
	private int expectedTimeToWatch;
	private int expectedTimeToAddStandup;
	private IReadAppPreferences mockPreferences;

	@Before
	public void initializeMocks() {
		this.expectedDay = randI();
		this.expectedTimeToWatch = randI();
		this.expectedTimeToAddStandup = this.expectedTimeToWatch + 1;

		this.mockDataController = useMock(DataController.class);
		this.mockTimeblockOnStandup = useMock(IReadTimeblock.class);
		this.mockPreferences = useMock(IReadAppPreferences.class);

		this.timeDataBeforeStandup = new TimeData("my project", "not standup", "leading dog");
	}

	@Test
	public void testImplementsInterface() throws Exception {
		assertImplementsInterface(IControlObserver.class, AutoStandupObserver.class);
	}

	@Test
	public void testConstructorParam() throws Exception {
		startReplay();
		AutoStandupObserver autoStandupObserver = createObserverForTesting();
		assertConstructionParamSame("preferences", this.mockPreferences, autoStandupObserver);
		assertConstructionParamSame("dataController", this.mockDataController, autoStandupObserver);
		assertConstructionParameterEquals("dayIndex", this.expectedDay, autoStandupObserver);
		assertConstructionParameterEquals("blockIndex", this.expectedTimeToWatch, autoStandupObserver);
	}

	@Test
	public void testTimeblockSetChanged_AddsTimeblockForStandup() throws Exception {
		TimeblockSet mockTimeblockSet = createTimeblockSetWithTargetBlock();
		setStandupBlockOccupiedState(false);
		expect(this.mockPreferences.autoStandup()).andReturn(true);

		expect(
				this.mockDataController.createTimeblockSet(this.expectedDay, this.expectedTimeToAddStandup,
						this.expectedTimeToAddStandup)).andReturn(mockTimeblockSet);

		Capture<TimeData> timedataCapture = Capture.newInstance();
		this.mockDataController.updateBlockData(eq(mockTimeblockSet), capture(timedataCapture));

		startReplay();
		AutoStandupObserver autoStandupObserver = createObserverForTesting();
		autoStandupObserver.timeblockSetChanged(mockTimeblockSet);
		stopReplay();
		TimeData actualTimeData = timedataCapture.getValue();
		assertEquals(this.timeDataBeforeStandup.getProject(), actualTimeData.getProject());
		assertEquals("STU", actualTimeData.getCard());
		assertEquals(this.timeDataBeforeStandup.getRole(), actualTimeData.getRole());
	}

	private AutoStandupObserver createObserverForTesting() {
		return new AutoStandupObserver(this.mockPreferences, this.mockDataController, this.expectedDay,
				this.expectedTimeToWatch);
	}

	@Test
	public void testTimeblockSetChanged_DoesNothingIfPreferenceIsNotSelected() throws Exception {
		expect(this.mockPreferences.autoStandup()).andReturn(false);
		TimeblockSet mockTimeblockSet = useMock(TimeblockSet.class);
		startReplay();
		AutoStandupObserver autoStandupObserver = createObserverForTesting();
		autoStandupObserver.timeblockSetChanged(mockTimeblockSet);
		stopReplay();
	}

	@Test
	public void testTimeblockSetChanged_DoesNothingIfNextBlockIsOccupied() throws Exception {
		TimeblockSet mockTimeblockSet = createTimeblockSetWithTargetBlock();
		setStandupBlockOccupiedState(true);
		expect(this.mockPreferences.autoStandup()).andReturn(true);

		startReplay();
		AutoStandupObserver autoStandupObserver = createObserverForTesting();
		autoStandupObserver.timeblockSetChanged(mockTimeblockSet);
		stopReplay();
	}

	@Test
	public void testTimeblockSetChanged_DoesNothingIfSetDoesNotBelongToTargetDay() throws Exception {
		expect(this.mockPreferences.autoStandup()).andReturn(true);
		TimeblockSet mockTimeblockSet = useMock(TimeblockSet.class);
		expect(mockTimeblockSet.getBlockSet()).andReturn(new ArrayList<>());

		startReplay();
		AutoStandupObserver autoStandupObserver = createObserverForTesting();
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
