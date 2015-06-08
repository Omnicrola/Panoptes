package com.omnicrola.panoptes.ui.preferences;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.ui.CloseDialogListener;
import com.omnicrola.util.ConstructorParameter;

public class PreferencesDialogControllerFactory {

	@ConstructorParameter("dataController")
	private DataController dataController;

	public PreferencesDialogControllerFactory(DataController dataController) {
		this.dataController = dataController;
	}

	public void wire(IPreferencesView preferencesView) {
		preferencesView.setAutoStandup(this.dataController.getPreferences().autoStandup());
		preferencesView.addSaveListener(new PreferencesSaveListener(this.dataController, preferencesView));
		preferencesView.addCancelListener(new CloseDialogListener(preferencesView));
	}

}
