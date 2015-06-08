package com.omnicrola.panoptes.ui.preferences;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;

import java.awt.event.ActionListener;

import org.easymock.Capture;
import org.junit.Test;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.testing.util.EnhancedTestCase;

public class PreferencesSaveListenerTest extends EnhancedTestCase {

	@Test
	public void testImplementsInterface() throws Exception {
		assertImplementsInterface(ActionListener.class, PreferencesSaveListener.class);
	}

	@Test
	public void testConstructorParameters() throws Exception {
		DataController mockDataContoller = useMock(DataController.class);
		IPreferencesView mockView = useMock(IPreferencesView.class);
		PreferencesSaveListener preferencesSaveListener = new PreferencesSaveListener(mockDataContoller, mockView);
		assertConstructionParamSame("dataController", mockDataContoller, preferencesSaveListener);
		assertConstructionParamSame("preferencesView", mockView, preferencesSaveListener);
	}

	@Test
	public void testActionPerformedSavesAndCloses() throws Exception {
		boolean expectedStandupState = randomBoolean();
		DataController mockDataContoller = useMock(DataController.class);
		IPreferencesView mockView = useMock(IPreferencesView.class);
		expect(mockView.getAutoStandup()).andReturn(expectedStandupState);
		Capture<AppPreferences> capturePreferences = Capture.newInstance();
		mockDataContoller.setPreferences(capture(capturePreferences));
		expectLastCall().once();
		mockView.closeDisplay();
		expectLastCall().once();

		startReplay();
		PreferencesSaveListener preferencesSaveListener = new PreferencesSaveListener(mockDataContoller, mockView);
		preferencesSaveListener.actionPerformed(null);

		stopReplay();
		AppPreferences newPreferences = capturePreferences.getValue();
		assertEquals(expectedStandupState, newPreferences.autoStandup());
	}

	private boolean randomBoolean() {
		return Math.random() < 0.5;
	}
}
