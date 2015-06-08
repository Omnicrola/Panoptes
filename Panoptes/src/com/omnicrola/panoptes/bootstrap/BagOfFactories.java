package com.omnicrola.panoptes.bootstrap;

import com.omnicrola.panoptes.control.AutoStandupObserver;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.data.MainDataModelFactory;
import com.omnicrola.panoptes.data.fileIO.xml.SettingsFileManager;
import com.omnicrola.panoptes.data.fileIO.xml.XmlSettings;
import com.omnicrola.panoptes.settings.IReadAppPreferences;
import com.omnicrola.util.ConstructorParameter;

public class BagOfFactories {

	private static final int NINE_FOURTY_FIVE = 15;
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
		XmlSettings settings = settingsFileManagerLoader.load();

		MainDataModel mainDataModel = this.mainDataModelFactory.build(settings.statements);
		mainDataModel.setPersonalData(settings.personalData);
		mainDataModel.setPreferences(settings.preferences);
		DataController dataController = buildDataController(mainDataModel, settings.preferences);

		this.shutdownSequenceFactory.buildAndAttachShutdownToRuntime(settingsFileManagerLoader, mainDataModel);
		return dataController;
	}

	private DataController buildDataController(MainDataModel mainDataModel, IReadAppPreferences preferences) {
		DataController dataController = this.dataControllerFactory.build(mainDataModel);
		dataController.addObserver(createStandupObserver(preferences, dataController, 2));
		dataController.addObserver(createStandupObserver(preferences, dataController, 3));
		dataController.addObserver(createStandupObserver(preferences, dataController, 4));
		dataController.addObserver(createStandupObserver(preferences, dataController, 5));
		dataController.addObserver(createStandupObserver(preferences, dataController, 6));
		return dataController;
	}

	private IControlObserver createStandupObserver(IReadAppPreferences preferences, DataController dataController,
			int dayIndex) {
		return new AutoStandupObserver(dataController, dayIndex, NINE_FOURTY_FIVE);
	}

	public MainWindowFactory getMainWindowFactory() {
		return this.mainWindowBuilder;
	}

}
