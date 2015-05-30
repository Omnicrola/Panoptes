package com.omnicrola.panoptes.bootstrap;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.MainDataModelFactory;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;
import com.omnicrola.panoptes.data.fileIO.xml.XMLSettings;
import com.omnicrola.util.ConstructorParameter;

public class BagOfFactories {

	@ConstructorParameter("settingsFileManagerFactory")
	private final SettingsFileManagerFactory settingsFileManagerFactory;
	@ConstructorParameter("mainDataModelFactory")
	private final MainDataModelFactory mainDataModelFactory;
	@ConstructorParameter("dataControllerFactory")
	private final DataControllerFactory dataControllerFactory;
	@ConstructorParameter("shutdownSequenceFactory")
	private final ShutdownSequenceFactory shutdownSequenceFactory;
	@ConstructorParameter("mainWindowBuilder")
	private final MainWindowFactory mainWindowBuilder;

	public BagOfFactories(SettingsFileManagerFactory settingsFileManagerFactory,
			MainDataModelFactory mainDataModelFactory, DataControllerFactory dataControllerFactory,
			ShutdownSequenceFactory shutdownSequenceFactory, MainWindowFactory mainWindowBuilder) {
		this.settingsFileManagerFactory = settingsFileManagerFactory;
		this.mainDataModelFactory = mainDataModelFactory;
		this.dataControllerFactory = dataControllerFactory;
		this.shutdownSequenceFactory = shutdownSequenceFactory;
		this.mainWindowBuilder = mainWindowBuilder;
	}

	public DataController buildDataController() {
		SettingsFileManager settingsFileManagerLoader = this.settingsFileManagerFactory.build();
		XMLSettings settings = settingsFileManagerLoader.load();

		MainDataModel mainDataModel = this.mainDataModelFactory.build(settings.statements);
		DataController dataController = this.dataControllerFactory.build(mainDataModel);
		dataController.setPersonalData(settings.personalData);

		this.shutdownSequenceFactory.buildAndAttachShutdownToRuntime(settingsFileManagerLoader, mainDataModel);
		return dataController;
	}

	public MainWindowFactory getMainWindowFactory() {
		return this.mainWindowBuilder;
	}

}
