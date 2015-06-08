package com.omnicrola.panoptes.ui.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class PreferencesViewTest extends EnhancedTestCase {

	private static final String BUTTON_SAVE = "button-save";
	private static final String BUTTON_CANCEL = "button-cancel";
	private static final String AUTO_STANDUP_CHECKBOX = "auto-standup-checkbox";
	private static final String EXPECTED_TOOLTIP_TEXT = "When enabled: automatically creates an entry for Standup (STU) at 10:00 when you create an entry at 9:45";

	@Test
	public void testExtendsJDialog() throws Exception {
		assertIsSuperclass(PreferencesView.class, JDialog.class);
		assertImplementsInterface(IPreferencesView.class, PreferencesView.class);
	}

	@Test
	public void testDialogSettings() throws Exception {
		PreferencesView preferencesView = new PreferencesView();
		assertEquals(new Dimension(300, 300), preferencesView.getSize());
		assertEquals("Preferences", preferencesView.getTitle());
		assertTrue(preferencesView.isModal());
	}

	@Test
	public void testRequiredComponents() throws Exception {
		PreferencesView preferencesView = new PreferencesView();
		JCheckBox autostandupCheckbox = getStandupCheckbox(preferencesView);
		assertFalse(autostandupCheckbox.isSelected());
		assertEquals("Automatic Standup", autostandupCheckbox.getText());
		assertEquals(EXPECTED_TOOLTIP_TEXT, autostandupCheckbox.getToolTipText());

		JButton saveButton = getSaveButton(preferencesView);
		assertEquals("Save", saveButton.getText());
		JButton cancelButton = getCancelButton(preferencesView);
		assertEquals("Cancel", cancelButton.getText());
	}

	@Test
	public void testGetSetCheckboxState() throws Exception {
		PreferencesView preferencesView = new PreferencesView();
		JCheckBox standupCheckbox = getStandupCheckbox(preferencesView);
		preferencesView.setAutoStandup(true);
		assertTrue(standupCheckbox.isSelected());
		assertTrue(preferencesView.getAutoStandup());
		preferencesView.setAutoStandup(false);
		assertFalse(standupCheckbox.isSelected());
		assertFalse(preferencesView.getAutoStandup());

		standupCheckbox.setSelected(true);
		assertTrue(preferencesView.getAutoStandup());
	}

	@Test
	public void testAddButtonListeners() throws Exception {
		ActionListener mockListener1 = useMock(ActionListener.class);
		ActionListener mockListener2 = useMock(ActionListener.class);
		startReplay();

		PreferencesView preferencesView = new PreferencesView();
		JButton cancelButton = getCancelButton(preferencesView);
		JButton saveButton = getSaveButton(preferencesView);
		preferencesView.addSaveListener(mockListener1);
		preferencesView.addCancelListener(mockListener2);

		checkListenerIsPresent(mockListener1, saveButton);
		checkListenerIsPresent(mockListener2, cancelButton);
	}

	private void checkListenerIsPresent(ActionListener mockListener1, JButton saveButton) {
		ActionListener[] actionListeners = saveButton.getActionListeners();
		assertEquals(1, actionListeners.length);
		assertSame(mockListener1, actionListeners[0]);
	}

	private JCheckBox getStandupCheckbox(PreferencesView preferencesView) {
		JCheckBox autostandupCheckbox = getComponentByName(preferencesView.getContentPane(), AUTO_STANDUP_CHECKBOX,
				JCheckBox.class);
		return autostandupCheckbox;

	}

	private JButton getCancelButton(PreferencesView preferencesView) {
		JButton cancelButton = getComponentByName(preferencesView.getContentPane(), BUTTON_CANCEL, JButton.class);
		return cancelButton;
	}

	private JButton getSaveButton(PreferencesView preferencesView) {
		JButton saveButton = getComponentByName(preferencesView.getContentPane(), BUTTON_SAVE, JButton.class);
		return saveButton;
	}
}
