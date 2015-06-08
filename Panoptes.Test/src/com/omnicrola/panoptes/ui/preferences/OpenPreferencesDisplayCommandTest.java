package com.omnicrola.panoptes.ui.preferences;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.expectLastCall;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class OpenPreferencesDisplayCommandTest extends EnhancedTestCase {

	@Test
	public void testConstructorParameters() throws Exception {
		PreferencesViewFactory mockViewFactory = useMock(PreferencesViewFactory.class);
		PreferencesDialogControllerFactory mockControllerFactory = useMock(PreferencesDialogControllerFactory.class);
		OpenPreferencesDisplayCommand openPreferencesDisplayCommand = new OpenPreferencesDisplayCommand(
				mockViewFactory, mockControllerFactory);
		assertConstructionParamSame("controllerFactory", mockControllerFactory, openPreferencesDisplayCommand);
	}

	@Test
	public void testActionPerformed() throws Exception {
		PreferencesView mockView = useMock(PreferencesView.class);
		PreferencesViewFactory mockViewFactory = useMock(PreferencesViewFactory.class);
		PreferencesDialogControllerFactory mockControllerFactory = useMock(PreferencesDialogControllerFactory.class);
		expect(mockViewFactory.build()).andReturn(mockView).once();
		mockControllerFactory.wire(mockView);
		expectLastCall().once();
		mockView.showDisplay();
		expectLastCall().once();

		startReplay();
		OpenPreferencesDisplayCommand openPreferencesDisplayCommand = new OpenPreferencesDisplayCommand(
				mockViewFactory, mockControllerFactory);
		openPreferencesDisplayCommand.actionPerformed(null);
	}
}
