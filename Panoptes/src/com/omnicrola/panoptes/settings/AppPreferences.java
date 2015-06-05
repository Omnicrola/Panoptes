package com.omnicrola.panoptes.settings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class AppPreferences implements IReadAppPreferences {

	@XmlElement(name = "AutoStandup")
	private boolean autoStandup;

	public boolean autoStandup() {
		return this.autoStandup;
	}

	public void setAutoStandup(boolean autoStandup) {
		this.autoStandup = autoStandup;
	}
}
