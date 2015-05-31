package com.omnicrola.panoptes.ui.listener;

import static org.powermock.api.easymock.PowerMock.expectLastCall;

import java.awt.event.FocusListener;

import javax.swing.JTextField;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class TextHighlightingFocusListenerTest extends EnhancedTestCase {

	@Test
	public void testImplementsInterface() throws Exception {
		assertImplementsInterface(FocusListener.class, TextHighlightingFocusListener.class);
	}

	@Test
	public void testConstructorParameters() throws Exception {
		JTextField jTextField = new JTextField();
		TextHighlightingFocusListener listener = new TextHighlightingFocusListener(jTextField);
		assertConstructionParamSame("textField", jTextField, listener);
	}

	@Test
	public void testFocusGained() throws Exception {
		JTextField mockTextField = useMock(JTextField.class);
		mockTextField.selectAll();
		expectLastCall().once();

		startReplay();
		TextHighlightingFocusListener listener = new TextHighlightingFocusListener(mockTextField);
		listener.focusGained(null);
	}

	@Test
	public void testFocusLostDoesNothing() throws Exception {
		JTextField mockTextField = useMock(JTextField.class);

		startReplay();
		TextHighlightingFocusListener listener = new TextHighlightingFocusListener(mockTextField);
		listener.focusLost(null);
	}
}
