package com.omnicrola.panoptes.ui.preferences;

import static org.powermock.api.easymock.PowerMock.expectLastCall;

import java.awt.event.ActionListener;

import org.junit.Test;

import com.omnicrola.panoptes.ui.CloseDialogListener;
import com.omnicrola.panoptes.ui.IDialog;
import com.omnicrola.testing.util.EnhancedTestCase;

public class CloseDialogListenerTest extends EnhancedTestCase {

	@Test
	public void testImplementsInterface() throws Exception {
		assertImplementsInterface(ActionListener.class, CloseDialogListener.class);
	}

	@Test
	public void testConstructorParameters() throws Exception {
		IDialog mockDialog = useMock(IDialog.class);
		CloseDialogListener closeDialogListener = new CloseDialogListener(mockDialog);
		assertConstructionParamSame("dialog", mockDialog, closeDialogListener);
	}

	@Test
	public void testActionPerformed() throws Exception {
		IDialog mockDialog = useMock(IDialog.class);
		mockDialog.closeDisplay();
		expectLastCall().once();
		CloseDialogListener closeDialogListener = new CloseDialogListener(mockDialog);
		startReplay();
		closeDialogListener.actionPerformed(null);
	}
}
