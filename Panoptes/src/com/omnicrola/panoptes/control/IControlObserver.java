package com.omnicrola.panoptes.control;

public interface IControlObserver {

    public abstract void currentFilenameChanged(String filename);

    public abstract void dataChanged();

    public abstract void timeblockSetChanged(TimeblockSet timeblockSet);

}
