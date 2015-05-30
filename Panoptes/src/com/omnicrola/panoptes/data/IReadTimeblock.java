package com.omnicrola.panoptes.data;

import com.omnicrola.panoptes.control.TimeblockSet;

public interface IReadTimeblock {

    public abstract int getColumnIndex();

    public abstract int getDayIndex();

    public abstract TimeblockSet getParentSelection();

    public abstract TimeData getTimeData();

    public abstract boolean isOccupied();

}
