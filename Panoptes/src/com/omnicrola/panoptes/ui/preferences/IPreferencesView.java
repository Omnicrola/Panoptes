package com.omnicrola.panoptes.ui.preferences;

import java.awt.event.ActionListener;

import com.omnicrola.panoptes.ui.IDialog;

public interface IPreferencesView extends IDialog {

	public abstract void addCancelListener(ActionListener actionListener);

	public abstract void addSaveListener(ActionListener actionListener);

	public abstract void setAutoStandup(boolean isSelected);

	public abstract boolean getAutoStandup();

}
