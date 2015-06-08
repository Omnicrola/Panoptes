package com.omnicrola.panoptes.ui.preferences;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class PreferencesViewFactoryTest extends EnhancedTestCase {

	@Test
	public void testBuild() throws Exception {

		PreferencesViewFactory preferencesViewFactory = new PreferencesViewFactory();
		assertIsOfTypeAndGet(PreferencesView.class, preferencesViewFactory.build());
	}
}
