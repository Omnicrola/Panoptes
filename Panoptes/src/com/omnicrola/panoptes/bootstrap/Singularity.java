package com.omnicrola.panoptes.bootstrap;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.BottomPanelFactory;
import com.omnicrola.panoptes.TopPanelFactory;
import com.omnicrola.panoptes.data.MainDataModelFactory;
import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopupFactory;

public class Singularity {

	public static BagOfFactories bang() {
		AppSettings settings = AppSettings.INSTANCE;

		SettingsFileManagerFactory workStatementManagerFactory = new SettingsFileManagerFactory(settings);

		MainDataModelFactory mainDataModelFactory = new MainDataModelFactory(settings);
		DataControllerFactory dataControllerFactory = new DataControllerFactory(settings);
		ShutdownSequenceFactory shutdownSequenceFactory = new ShutdownSequenceFactory();
		MenuFactory menuFactory = new MenuFactory(new MenuItemFactory(), settings);

		AutoCompletePopupFactory autoCompleteFactory = new AutoCompletePopupFactory();
		BottomPanelFactory bottomPanelFactory = new BottomPanelFactory(settings);
		TopPanelFactory topPanelFactory = new TopPanelFactory(settings, autoCompleteFactory);
		MainWindowFactory mainWindowBuilder = new MainWindowFactory(settings, menuFactory, topPanelFactory,
				bottomPanelFactory);

		return new BagOfFactories(workStatementManagerFactory, mainDataModelFactory, dataControllerFactory,
				shutdownSequenceFactory, mainWindowBuilder);
	}

}
