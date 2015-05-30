package com.omnicrola.panoptes.bootstrap;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;
import com.omnicrola.panoptes.data.fileIO.xml.XMLSettings;
import com.omnicrola.panoptes.data.fileIO.xml.XmlFileAdapter;
import com.omnicrola.util.ConstructorParameter;

public class SettingsFileManagerFactory {

	@ConstructorParameter("settings")
	private final AppSettings settings;

	public SettingsFileManagerFactory(AppSettings settings) {
		this.settings = settings;
	}

	public SettingsFileManager build() {
		return new SettingsFileManager(this.settings, new XmlFileAdapter<XMLSettings>(XMLSettings.class));
	}

}
