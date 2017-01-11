package com.omnicrola.panoptes.data.fileIO.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.data.ProjectGroup;
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

	//@formatter:off
	private List<WorkStatement> buildDefaultWorkStatements() {
		ArrayList<WorkStatement> workStatementList = new ArrayList<WorkStatement>();
		workStatementList.add(new WorkStatement("PRESALES", "Menlo", "PRESALES", "NYE", 0, ProjectGroup.INTERNAL_SUPPORT));
		workStatementList.add(new WorkStatement("MARKETING", "Menlo", "MARKETING", "NYE", 0, ProjectGroup.INTERNAL_SUPPORT));
		workStatementList.add(new WorkStatement("PERSONNEL", "Menlo", "PERSONNEL", "NYE", 0, ProjectGroup.INTERNAL_SUPPORT));
		workStatementList.add(new WorkStatement("A/O/F", "Menlo", "A/O/F", "NYE", 0, ProjectGroup.INTERNAL_SUPPORT));
		workStatementList.add(new WorkStatement("IT SUPPORT", "Menlo", "IT", "NYE", 0, ProjectGroup.INTERNAL_SUPPORT));
		return workStatementList;
	}
	//@formatter:on

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
