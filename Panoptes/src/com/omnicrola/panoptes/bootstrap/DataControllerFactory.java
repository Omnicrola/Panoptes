package com.omnicrola.panoptes.bootstrap;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.util.ConstructorParameter;

public class DataControllerFactory {

	@ConstructorParameter("settings")
	private final AppSettings settings;

	public DataControllerFactory(AppSettings settings) {
		this.settings = settings;
	}

	public DataController build(MainDataModel mainDataModel) {
		return new DataController(mainDataModel, this.settings);
	}

}
