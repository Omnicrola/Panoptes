package com.omnicrola.panoptes.ui.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.settings.AppPreferences;
import com.omnicrola.util.ConstructorParameter;

public class PreferencesSaveListener implements ActionListener {

	@ConstructorParameter("dataController")
	private DataController dataContoller;
	@ConstructorParameter("preferencesView")
	private IPreferencesView preferencesView;

	public PreferencesSaveListener(DataController dataContoller, IPreferencesView preferencesView) {
		this.dataContoller = dataContoller;
		this.preferencesView = preferencesView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AppPreferences appPreferences = new AppPreferences();
		appPreferences.setAutoStandup(this.preferencesView.getAutoStandup());
		this.dataContoller.setPreferences(appPreferences);
		this.preferencesView.closeDisplay();
	}

}
