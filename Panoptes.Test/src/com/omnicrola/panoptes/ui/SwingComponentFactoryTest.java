package com.omnicrola.panoptes.ui;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class SwingComponentFactoryTest extends EnhancedTestCase {

	@Test
	public void testCreateJLabel() throws Exception {
		String expectedLabel = "hello there";
		int expectedWidth = randI();
		JLabel label = SwingComponentFactory.createLabel(expectedLabel, expectedWidth);

		assertEquals(expectedLabel, label.getText());
		assertEquals(new Dimension(expectedWidth, 20), label.getPreferredSize());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateComboBox() throws Exception {
		String[] expectedOptions = new String[] { "test", "another", "thingmabob" };
		JComboBox<String> comboBox = SwingComponentFactory.createComboBox(expectedOptions);

		DefaultComboBoxModel<String> comboBoxModel = assertIsOfTypeAndGet(DefaultComboBoxModel.class,
				comboBox.getModel());
		Vector<String> vector = reflectFieldFromObject(Vector.class, "objects", comboBoxModel);
		checkVectorMatchesArray(expectedOptions, vector);
	}

	@Test
	public void testCreatePanel() throws Exception {
		assertIsOfTypeAndGet(JPanel.class, SwingComponentFactory.createPanel());
	}

	public static <T> void checkVectorMatchesArray(T[] expectedOptions, Vector<T> vector) {
		assertEquals(expectedOptions.length, vector.size());
		for (T string : expectedOptions) {
			vector.contains(string);
		}
	}

}
