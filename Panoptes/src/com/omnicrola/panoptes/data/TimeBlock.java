package com.omnicrola.panoptes.data;

import com.omnicrola.panoptes.control.TimeblockSet;

public class TimeBlock implements IReadTimeblock {

    public static final TimeBlock EMPTY = new TimeBlock(0, 0) {

        @Override
        public void setOccupied(boolean isOccupied) {
        }

        @Override
        public void setParentSelection(TimeblockSet parentSelection) {
        }

        @Override
        public void setTimeData(TimeData data) {
        };
    };

    private final int day;

    private final int blockIndex;
    private TimeData data;
    private TimeblockSet parentSet;
    private boolean isOccupied;

    public TimeBlock(int day, int blockIndex) {
        this.day = day;
        this.blockIndex = blockIndex;
        this.parentSet = TimeblockSet.EMPTY;
        this.data = TimeData.NULL;
    }

    public void clear() {
        this.parentSet = TimeblockSet.EMPTY;
        this.data = TimeData.NULL;
        this.isOccupied = false;
    }

    @Override
    public int getColumnIndex() {
        return this.blockIndex;
    }

    @Override
    public int getDayIndex() {
        return this.day;
    }

    @Override
    public TimeblockSet getParentSelection() {
        return this.parentSet;
    }

    @Override
    public TimeData getTimeData() {
        return this.data;
    }

    @Override
    public boolean isOccupied() {
        return this.isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public void setParentSelection(TimeblockSet parentSelection) {
        this.parentSet.remove(this);
        this.parentSet = parentSelection;
    }

    public void setTimeData(TimeData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TimeBlock" + hashCode() + "[day=" + this.day + ", blockIndex=" + this.blockIndex
                + ", isOccupied=" + this.isOccupied + "]";
    }

}
