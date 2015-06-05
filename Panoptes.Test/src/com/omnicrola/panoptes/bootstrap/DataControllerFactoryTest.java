package com.omnicrola.panoptes.bootstrap;

import org.junit.Test;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.MainDataModel;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.testing.util.EnhancedTestCase;

public class DataControllerFactoryTest extends EnhancedTestCase {

	@Test
	public void testConstructorParams() throws Exception {
		AppSettings mockSettings = useMock(AppSettings.class);
		startReplay();

		DataControllerFactory factory = new DataControllerFactory(mockSettings);
		assertConstructionParamSame("settings", mockSettings, factory);
	}

	@Test
	public void testBuild() throws Exception {
		AppSettings mockSettings = useMock(AppSettings.class);
		MainDataModel mockModel = useMock(MainDataModel.class);

		startReplay();
		DataControllerFactory factory = new DataControllerFactory(mockSettings);
		DataController dataController = factory.build(mockModel);
		assertConstructionParamSame("dataModel", mockModel, dataController);
		assertConstructionParamSame("settings", mockSettings, dataController);

	}
}
