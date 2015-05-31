package com.omnicrola.panoptes.ui;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SwingComponentFactory {

	public static JLabel createLabel(String expectedLabel, int expectedWidth) {
		JLabel jLabel = new JLabel(expectedLabel, SwingConstants.RIGHT);
		jLabel.setPreferredSize(new Dimension(expectedWidth, 20));
		return jLabel;
	}

	public static <T> JComboBox<T> createComboBox(T[] options) {
		return new JComboBox<T>(options);
	}

	public static JPanel createPanel() {
		return new JPanel();
	}

}
