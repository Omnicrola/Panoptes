package com.omnicrola.panoptes.ui.preferences;

import java.awt.event.ActionListener;


public interface IPreferencesView {

	public abstract void addCancelListener(ActionListener actionListener);

	public abstract void addSaveListener(ActionListener actionListener);

	public abstract void setAutoStandup(boolean isSelected);

	public abstract void closeDisplay();

	public abstract void showDisplay();

}
