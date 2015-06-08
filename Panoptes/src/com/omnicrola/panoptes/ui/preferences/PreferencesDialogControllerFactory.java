package com.omnicrola.panoptes.ui.preferences;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.util.ConstructorParameter;

public class PreferencesDialogControllerFactory {

	@ConstructorParameter("dataController")
	private DataController dataController;

	public PreferencesDialogControllerFactory(DataController dataController) {
		this.dataController = dataController;

	}

	public void wire(IPreferencesView mockView) {

	}

}
