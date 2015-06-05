package com.omnicrola.panoptes.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class AppPreferencesTest extends EnhancedTestCase {

	@Test
	public void testImplementsInterface() throws Exception {
		assertImplementsInterface(IReadAppPreferences.class, AppPreferences.class);
	}

	@Test
	public void testHasXmlAnnotation() throws Exception {
		XmlAccessorType xmlAnnotation = assertAnnotationIsOnClass(AppPreferences.class, XmlAccessorType.class);
		assertEquals(XmlAccessType.NONE, xmlAnnotation.value());
		assertXmlElementOnField(AppPreferences.class, "autoStandup", "AutoStandup");
	}

	@Test
	public void testInterfaceGetters() throws Exception {
		AppPreferences appPreferences = new AppPreferences();
		appPreferences.setAutoStandup(false);
		assertFalse(appPreferences.autoStandup());
		appPreferences.setAutoStandup(true);
		assertTrue(appPreferences.autoStandup());
	}
}
