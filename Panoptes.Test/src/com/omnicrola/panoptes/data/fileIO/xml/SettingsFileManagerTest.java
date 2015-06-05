package com.omnicrola.panoptes.data.fileIO.xml;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.data.fileIO.PanoptesException;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class SettingsFileManagerTest extends EnhancedTestCase {

	private SettingsFileManager settingsFileManager;
	private XmlFileAdapter<XmlSettings> fileAdapter;
	private AppSettings appSettings;

	@SuppressWarnings("unchecked")
	@Before
	public void Setup() {
		this.fileAdapter = useMock(XmlFileAdapter.class);
		this.appSettings = useMock(AppSettings.class);
		this.settingsFileManager = new SettingsFileManager(this.appSettings, this.fileAdapter);

	}

	@Test
	public void testConstructorParams() throws Exception {
		startReplay();
		assertConstructionParamSame("settings", this.appSettings, this.settingsFileManager);
		assertConstructionParamSame("fileAdapter", this.fileAdapter, this.settingsFileManager);
	}

	@Test
	public void testLoadSettings_NormalCase() throws Exception {
		File mockFile = useMock(File.class);
		XmlSettings mockSettings = useMock(XmlSettings.class);
		expect(this.appSettings.getSettingsSaveLocation()).andReturn(mockFile);
		expect(this.fileAdapter.loadObject(mockFile)).andReturn(mockSettings);
		startReplay();

		XmlSettings actualSettings = this.settingsFileManager.load();
		assertSame(mockSettings, actualSettings);
		stopReplay();
	}

	@Test
	public void testLoadSettings_ExceptionResultsInDefaults() throws Exception {
		File mockFile = useMock(File.class);
		expect(this.appSettings.getSettingsSaveLocation()).andReturn(mockFile);
		expect(this.fileAdapter.loadObject(mockFile)).andThrow(new PanoptesException("oh no"));
		startReplay();

		XmlSettings actualSettings = this.settingsFileManager.load();
		stopReplay();

		checkPersonalDataIsBlank(actualSettings);
		checkStatementsOfWork(actualSettings);
		checkPreferences(actualSettings);
	}

	private void checkPreferences(XmlSettings actualSettings) {
		AppPreferences preferences = actualSettings.preferences;
		assertTrue(preferences.autoStandup());

	}

	private void checkStatementsOfWork(XmlSettings actualSettings) {
		List<WorkStatement> statements = actualSettings.statements;
		assertEquals(7, statements.size());

		assertEquals(new WorkStatement("P-2 Presales", "Menlo", "P00002", "NYE", 0), statements.get(0));
		assertEquals(new WorkStatement("P-3 General", "Menlo", "P00003", "NYE", 0), statements.get(1));
		assertEquals(new WorkStatement("P-5 Marketing", "Menlo", "P00005", "NYE", 0), statements.get(2));
		assertEquals(new WorkStatement("P-7 Personnel", "Menlo", "P00007", "NYE", 0), statements.get(3));
		assertEquals(new WorkStatement("P-10 Operations", "Menlo", "P000010", "NYE", 0), statements.get(4));
		assertEquals(new WorkStatement("P-15 IT", "Menlo", "P000015", "NYE", 0), statements.get(5));
		assertEquals(new WorkStatement("P-16 Facilities", "Menlo", "P000016", "NYE", 0), statements.get(6));

	}

	private void checkPersonalDataIsBlank(XmlSettings actualSettings) {
		PersonalData personalData = actualSettings.personalData;
		assertEquals("", personalData.getFirstName());
		assertEquals("", personalData.getLastName());
		assertEquals("", personalData.getCompanyName());
		assertEquals("", personalData.getEmail());
		assertEquals("", personalData.getPhone());
		assertEquals("", personalData.getAddress());
		assertEquals("", personalData.getCity());
		assertEquals("", personalData.getState());
		assertEquals("", personalData.getZip());
	}
}
