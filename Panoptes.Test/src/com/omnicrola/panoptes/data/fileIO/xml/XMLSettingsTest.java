package com.omnicrola.panoptes.data.fileIO.xml;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;

import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.panoptes.settings.PersonalData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class XMLSettingsTest extends EnhancedTestCase {

	@Test
	public void testXmlAnnotations() throws Exception {
		XmlRootElement rootElementAnnotation = assertAnnotationIsOnClass(XmlSettings.class, XmlRootElement.class);
		assertEquals("settings", rootElementAnnotation.name());
		XmlAccessorType accessTypeAnnotation = assertAnnotationIsOnClass(XmlSettings.class, XmlAccessorType.class);
		assertEquals(XmlAccessType.NONE, accessTypeAnnotation.value());

		assertXmlElementOnField(XmlSettings.class, "personalData", "PersonalInfo");
		assertXmlElementOnField(XmlSettings.class, "statements", "WorkStatement");
		assertXmlElementOnField(XmlSettings.class, "preferences", "Preferences");
	}

	@Test
	public void testDefaults() throws Exception {
		XmlSettings xmlSettings = new XmlSettings();
		assertIsOfTypeAndGet(PersonalData.class, xmlSettings.personalData);
		assertIsOfTypeAndGet(AppPreferences.class, xmlSettings.preferences);
		assertNotNull(xmlSettings.statements);
		assertEquals(0, xmlSettings.statements.size());
	}
}
