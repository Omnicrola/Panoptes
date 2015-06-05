package com.omnicrola.panoptes.bootstrap;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;

import java.util.ArrayList;
import java.util.List;

import org.easymock.Capture;
import org.easymock.CaptureType;
import org.junit.Test;

import com.omnicrola.panoptes.control.AutoStandupObserver;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.MainDataModelFactory;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;
import com.omnicrola.panoptes.data.fileIO.xml.XmlSettings;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class BagOfFactoriesTest extends EnhancedTestCase {

	private SettingsFileManagerFactory mockSettingsFileFactory;
	private MainDataModelFactory mockDataModelFactory;
	private DataControllerFactory mockDataControllerFactory;
	private ShutdownSequenceFactory mockShutdownFactory;
	private MainWindowFactory mockWindowFactory;
	private PersonalData expectedPersonalData;
	private XmlSettings xmlSettings;
	private SettingsFileManager settingsFileManager;
	private MainDataModel mockDataModel;
	private DataController mockDataController;
	private Capture<IControlObserver> observerCapture;

	@Test
	public void testBuildDataController() throws Exception {
		initializeMocks();
		setupXmlSettings();
		setupExpectationsForFactoryCalls();

		startReplay();
		BagOfFactories bagOfFactories = createBagOfFactoriesForTesting();
		DataController actualDataController = bagOfFactories.buildDataController();
		assertSame(this.mockDataController, actualDataController);
		stopReplay();

		checkObserversWhereAddedToDataController();

	}

	private void initializeMocks() {
		this.mockSettingsFileFactory = useMock(SettingsFileManagerFactory.class);
		this.mockDataModelFactory = useMock(MainDataModelFactory.class);
		this.mockDataControllerFactory = useMock(DataControllerFactory.class);
		this.mockShutdownFactory = useMock(ShutdownSequenceFactory.class);
		this.mockWindowFactory = useMock(MainWindowFactory.class);
		this.settingsFileManager = useMock(SettingsFileManager.class);
		this.mockDataModel = useMock(MainDataModel.class);
		this.mockDataController = useMock(DataController.class);
		this.observerCapture = Capture.newInstance(CaptureType.ALL);
	}

	private void setupXmlSettings() {
		this.expectedPersonalData = useMock(PersonalData.class);
		this.xmlSettings = new XmlSettings();
		this.xmlSettings.personalData = this.expectedPersonalData;
		this.xmlSettings.statements = new ArrayList<>();
	}

	private BagOfFactories createBagOfFactoriesForTesting() {
		return new BagOfFactories(this.mockSettingsFileFactory, this.mockDataModelFactory,
				this.mockDataControllerFactory, this.mockShutdownFactory, this.mockWindowFactory);
	}

	private void setupExpectationsForFactoryCalls() {
		expect(this.mockSettingsFileFactory.build()).andReturn(this.settingsFileManager);
		expect(this.settingsFileManager.load()).andReturn(this.xmlSettings);
		expect(this.mockDataModelFactory.build(this.xmlSettings.statements)).andReturn(this.mockDataModel);
		expect(this.mockDataControllerFactory.build(this.mockDataModel)).andReturn(this.mockDataController);
		this.mockDataController.addObserver(capture(this.observerCapture));
		expectLastCall().anyTimes();
		this.mockDataController.setPersonalData(this.expectedPersonalData);
		expectLastCall().once();
		this.mockShutdownFactory.buildAndAttachShutdownToRuntime(this.settingsFileManager, this.mockDataModel);
		expectLastCall().once();
	}

	private void checkObserversWhereAddedToDataController() {
		assertTrue(this.observerCapture.hasCaptured());
		List<IControlObserver> observers = this.observerCapture.getValues();
		assertEquals(5, observers.size());
		checkStandupObserver(2, observers.get(0));
		checkStandupObserver(3, observers.get(1));
		checkStandupObserver(4, observers.get(2));
		checkStandupObserver(5, observers.get(3));
		checkStandupObserver(6, observers.get(4));
	}

	private void checkStandupObserver(int expectedDayIndex, IControlObserver controlObserver) {
		AutoStandupObserver observer = assertIsOfTypeAndGet(AutoStandupObserver.class, controlObserver);
		assertConstructionParameterEquals("dayIndex", expectedDayIndex, observer);
		assertConstructionParameterEquals("blockIndex", 15, observer);
	}

}
