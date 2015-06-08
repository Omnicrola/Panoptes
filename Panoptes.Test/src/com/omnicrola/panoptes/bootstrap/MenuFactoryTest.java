package com.omnicrola.panoptes.bootstrap;

import org.junit.Before;
import org.junit.Test;

import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.testing.util.EnhancedTestCase;

public class MenuFactoryTest extends EnhancedTestCase {

	private AppSettings mockAppSettings;
	private MenuItemFactory mockItemFactory;
	private MenuFactory menuFactory;

	@Before
	public void Setup() {
		this.mockAppSettings = useMock(AppSettings.class);
		this.mockItemFactory = useMock(MenuItemFactory.class);
		this.menuFactory = new MenuFactory(this.mockItemFactory, this.mockAppSettings);
	}

	@Test
	public void testConstructorParams() throws Exception {
		assertConstructionParamSame("settings", this.mockAppSettings, this.menuFactory);
		assertConstructionParamSame("itemFactory", this.mockItemFactory, this.menuFactory);

	}

}
