package com.omnicrola.panoptes.ui.preferences;

import org.junit.Test;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.testing.util.EnhancedTestCase;

public class PreferencesDialogControllerFactoryTest extends EnhancedTestCase {

	@Test
	public void testConstructorParameters() throws Exception {
		DataController mockDataController = useMock(DataController.class);
		PreferencesDialogControllerFactory preferencesDialogFactory = new PreferencesDialogControllerFactory(
				mockDataController);
		assertConstructionParamSame("dataController", mockDataController, preferencesDialogFactory);
	}

}
