package com.omnicrola.panoptes.bootstrap;

import org.junit.Test;

import com.omnicrola.panoptes.BottomPanelFactory;
import com.omnicrola.panoptes.TopPanelFactory;
import com.omnicrola.panoptes.data.MainDataModelFactory;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopupFactory;
import com.omnicrola.testing.util.EnhancedTestCase;

public class SingularityTest extends EnhancedTestCase {

	@Test
	public void testBangBuildsTheUniverseOfFactories() throws Exception {
		BagOfFactories bagOfFactories = Singularity.bang();
		checkSettingsFileManagerFactory(bagOfFactories);
		checkMainDataModelFactory(bagOfFactories);
		checkDataControllerFactory(bagOfFactories);
		assertConstructorParamAndGet(bagOfFactories, "shutdownSequenceFactory", ShutdownSequenceFactory.class);
		checkMainWindowFactory(bagOfFactories);

	}

	private void checkMainWindowFactory(BagOfFactories bagOfFactories) {
		MainWindowFactory mainWindowFactory = assertConstructorParamAndGet(bagOfFactories, "mainWindowBuilder",
				MainWindowFactory.class);
		checkHasAppSettingsInstance(mainWindowFactory);
		MenuFactory menuFactory = assertConstructorParamAndGet(mainWindowFactory, "menuFactory", MenuFactory.class);
		assertConstructorParamAndGet(menuFactory, "itemFactory", MenuItemFactory.class);
		checkHasAppSettingsInstance(menuFactory);

		TopPanelFactory topPanelFactory = assertConstructorParamAndGet(mainWindowFactory, "topPanelFactory",
				TopPanelFactory.class);
		checkHasAppSettingsInstance(topPanelFactory);
		assertConstructorParamAndGet(topPanelFactory, "autoCompletePopupFactory", AutoCompletePopupFactory.class);
		BottomPanelFactory bottomPanelFactory = assertConstructorParamAndGet(mainWindowFactory, "bottomPanelFactory",
				BottomPanelFactory.class);
		checkHasAppSettingsInstance(bottomPanelFactory);
	}

	private void checkDataControllerFactory(BagOfFactories bagOfFactories) {
		DataControllerFactory dataControllerFactory = assertConstructorParamAndGet(bagOfFactories,
				"dataControllerFactory", DataControllerFactory.class);
		checkHasAppSettingsInstance(dataControllerFactory);
	}

	private void checkMainDataModelFactory(BagOfFactories bagOfFactories) {
		MainDataModelFactory mainDataModelFactory = assertConstructorParamAndGet(bagOfFactories,
				"mainDataModelFactory", MainDataModelFactory.class);
		checkHasAppSettingsInstance(mainDataModelFactory);
	}

	private void checkSettingsFileManagerFactory(BagOfFactories bagOfFactories) {
		SettingsFileManagerFactory settingsFileManager = assertConstructorParamAndGet(bagOfFactories,
				"settingsFileManagerFactory", SettingsFileManagerFactory.class);
		checkHasAppSettingsInstance(settingsFileManager);
	}

	private void checkHasAppSettingsInstance(Object target) {
		assertConstructionParamSame("settings", AppSettings.INSTANCE, target);
	}
}
