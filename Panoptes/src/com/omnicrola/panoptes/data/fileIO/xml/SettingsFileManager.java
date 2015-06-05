package com.omnicrola.panoptes.data.fileIO.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.data.fileIO.PanoptesException;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.util.ConstructorParameter;

public class SettingsFileManager {

	@ConstructorParameter("settings")
	private final AppSettings settings;
	@ConstructorParameter("fileAdapter")
	private final XmlFileAdapter<XmlSettings> xmlFileReader;

	public SettingsFileManager(AppSettings settings, XmlFileAdapter<XmlSettings> xmlFileReader) {
		this.settings = settings;
		this.xmlFileReader = xmlFileReader;
	}

	private List<WorkStatement> buildDefaultWorkStatements() {
		ArrayList<WorkStatement> workStatementList = new ArrayList<WorkStatement>();
		workStatementList.add(new WorkStatement("P-2 Presales", "Menlo", "P00002", "NYE", 0));
		workStatementList.add(new WorkStatement("P-3 General", "Menlo", "P00003", "NYE", 0));
		workStatementList.add(new WorkStatement("P-5 Marketing", "Menlo", "P00005", "NYE", 0));
		workStatementList.add(new WorkStatement("P-7 Personnel", "Menlo", "P00007", "NYE", 0));
		workStatementList.add(new WorkStatement("P-10 Operations", "Menlo", "P000010", "NYE", 0));
		workStatementList.add(new WorkStatement("P-15 IT", "Menlo", "P000015", "NYE", 0));
		workStatementList.add(new WorkStatement("P-16 Facilities", "Menlo", "P000016", "NYE", 0));
		return workStatementList;
	}

	public XmlSettings load() {
		File fileSource = this.settings.getSettingsSaveLocation();
		try {
			XmlSettings worklist = this.xmlFileReader.loadObject(fileSource);
			return worklist;
		} catch (PanoptesException e) {
			XmlSettings xmlSettings = new XmlSettings();
			xmlSettings.personalData = new PersonalData();
			xmlSettings.statements = buildDefaultWorkStatements();
			xmlSettings.preferences = buildDefaultPreferences();
			return xmlSettings;
		}

	}

	private AppPreferences buildDefaultPreferences() {
		AppPreferences appPreferences = new AppPreferences();
		appPreferences.setAutoStandup(true);
		return appPreferences;
	}

	public void save(List<WorkStatement> workStatements, PersonalData personalData, AppPreferences appPreferences) {
		File settingsSaveLocation = this.settings.getSettingsSaveLocation();
		XmlSettings xmlSettings = new XmlSettings();
		xmlSettings.statements = workStatements;
		xmlSettings.personalData = personalData;
		xmlSettings.preferences = appPreferences;

		try {
			this.xmlFileReader.saveObject(xmlSettings, settingsSaveLocation);
		} catch (PanoptesException e) {
			e.printStackTrace();
		}

	}
}
