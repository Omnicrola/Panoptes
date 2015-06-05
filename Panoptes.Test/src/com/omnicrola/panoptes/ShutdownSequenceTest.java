package com.omnicrola.panoptes;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.expectLastCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class ShutdownSequenceTest extends EnhancedTestCase {

	@Test
	public void testExtendsThread() throws Exception {
		assertIsSuperclass(ShutdownSequence.class, Thread.class);
	}

	@Test
	public void testConstructorParams() throws Exception {
		MainDataModel mockModel = useMock(MainDataModel.class);
		SettingsFileManager mockSettingsManager = useMock(SettingsFileManager.class);
		startReplay();
		ShutdownSequence shutdownSequence = new ShutdownSequence(mockSettingsManager, mockModel);

		assertConstructionParamSame("dataModel", mockModel, shutdownSequence);
		assertConstructionParamSame("settingsFileManager", mockSettingsManager, shutdownSequence);
	}

	@Test
	public void testRun_SavesSettings() throws Exception {
		MainDataModel mockModel = useMock(MainDataModel.class);
		SettingsFileManager mockSettingsManager = useMock(SettingsFileManager.class);
		AppPreferences preferences = useMock(AppPreferences.class);
		PersonalData personalData = useMock(PersonalData.class);
		List<WorkStatement> statements = Collections.unmodifiableList(new ArrayList<>());

		expect(mockModel.getWorkStatements()).andReturn(statements);
		expect(mockModel.getPreferences()).andReturn(preferences);
		expect(mockModel.getPersonalData()).andReturn(personalData);

		mockSettingsManager.save(statements, personalData, preferences);
		expectLastCall().once();

		startReplay();
		ShutdownSequence shutdownSequence = new ShutdownSequence(mockSettingsManager, mockModel);
		shutdownSequence.run();
		stopReplay();
	}
}
