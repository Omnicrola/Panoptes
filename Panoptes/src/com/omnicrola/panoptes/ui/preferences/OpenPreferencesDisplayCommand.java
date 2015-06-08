package com.omnicrola.panoptes.ui.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omnicrola.util.ConstructorParameter;

public class OpenPreferencesDisplayCommand implements ActionListener {

	@ConstructorParameter("controllerFactory")
	private PreferencesDialogControllerFactory controllerFactory;
	@ConstructorParameter("viewFactory")
	private PreferencesViewFactory preferencesViewFactory;

	public OpenPreferencesDisplayCommand(PreferencesViewFactory preferencesViewFactory,
			PreferencesDialogControllerFactory controllerFactory) {
		this.preferencesViewFactory = preferencesViewFactory;
		this.controllerFactory = controllerFactory;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		IPreferencesView build = this.preferencesViewFactory.build();
		this.controllerFactory.wire(build);
		build.showDisplay();
	}

}
