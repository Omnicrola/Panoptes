package com.omnicrola.panoptes.ui.preferences;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;

import java.awt.event.ActionListener;

import org.easymock.Capture;
import org.junit.Test;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.settings.IReadAppPreferences;
import com.omnicrola.panoptes.ui.CloseDialogListener;
import com.omnicrola.testing.util.EnhancedTestCase;

public class PreferencesDialogControllerFactoryTest extends EnhancedTestCase {

	@Test
	public void testConstructorParameters() throws Exception {
		DataController mockDataController = useMock(DataController.class);
		PreferencesDialogControllerFactory preferencesDialogFactory = new PreferencesDialogControllerFactory(
				mockDataController);
		assertConstructionParamSame("dataController", mockDataController, preferencesDialogFactory);
	}

	@Test
	public void testWire() throws Exception {
		DataController mockDataController = useMock(DataController.class);
		IReadAppPreferences mockPreferences = useMock(IReadAppPreferences.class);
		IPreferencesView mockView = useMock(IPreferencesView.class);

		expect(mockDataController.getPreferences()).andReturn(mockPreferences);
		boolean expectedAutostandupState = randB();
		expect(mockPreferences.autoStandup()).andReturn(expectedAutostandupState);

		Capture<ActionListener> saveCapture = Capture.newInstance();
		Capture<ActionListener> cancelCapture = Capture.newInstance();
		mockView.addCancelListener(capture(cancelCapture));
		mockView.addSaveListener(capture(saveCapture));
		mockView.setAutoStandup(expectedAutostandupState);

		startReplay();
		PreferencesDialogControllerFactory controllerFactory = new PreferencesDialogControllerFactory(
				mockDataController);
		controllerFactory.wire(mockView);

		stopReplay();
		PreferencesSaveListener saveListener = assertIsOfTypeAndGet(PreferencesSaveListener.class,
				saveCapture.getValue());
		assertConstructionParamSame("dataController", mockDataController, saveListener);
		assertConstructionParamSame("preferencesView", mockView, saveListener);

		CloseDialogListener closeListener = assertIsOfTypeAndGet(CloseDialogListener.class, cancelCapture.getValue());
		assertConstructionParamSame("dialog", mockView, closeListener);

	}
}
