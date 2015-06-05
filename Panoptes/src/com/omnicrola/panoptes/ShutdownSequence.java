package com.omnicrola.panoptes;

import java.util.List;

import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.util.ConstructorParameter;

public class ShutdownSequence extends Thread {

	@ConstructorParameter("settingsFileManager")
	private final SettingsFileManager settingsFileManager;
	@ConstructorParameter("dataModel")
	private final MainDataModel mainDataModel;

	public ShutdownSequence(SettingsFileManager workStatementFileManager, MainDataModel mainDataModel) {
		this.settingsFileManager = workStatementFileManager;
		this.mainDataModel = mainDataModel;
	}

	@Override
	public void run() {
		List<WorkStatement> workStatements = this.mainDataModel.getWorkStatements();
		PersonalData personalData = this.mainDataModel.getPersonalData();
		AppPreferences preferences = this.mainDataModel.getPreferences();
		this.settingsFileManager.save(workStatements, personalData, preferences);
	}

}
