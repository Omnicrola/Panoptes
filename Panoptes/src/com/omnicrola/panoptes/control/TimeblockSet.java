package com.omnicrola.panoptes.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.omnicrola.panoptes.data.IReadTimeblock;

public class TimeblockSet {

    public static final TimeblockSet EMPTY = new TimeblockSet(new ArrayList<IReadTimeblock>());

    private final List<IReadTimeblock> listSelection;
    private int rowIndex;
    private int lowestColumnIndex;
    private int highestColumnIndex;

    public TimeblockSet(List<IReadTimeblock> listSelection) {
        this.listSelection = listSelection;
        updateMetadata();
    }

    public List<IReadTimeblock> getBlockSet() {
        return Collections.unmodifiableList(this.listSelection);
    }

    public int getHighestColumnIndex() {
        return this.highestColumnIndex;
    }

    public int getLowestColumnIndex() {
        return this.lowestColumnIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public TimeblockIndexIterator indexIterator() {
        return new TimeblockIndexIterator(new ArrayList<IReadTimeblock>(this.listSelection));
    }

    public void remove(IReadTimeblock timeBlock) {
        if (this.listSelection.contains(timeBlock)) {
            this.listSelection.remove(timeBlock);
            updateMetadata();
        }
    }

    private void updateMetadata() {
        if (this.listSelection.isEmpty()) {
            this.highestColumnIndex = 0;
            this.lowestColumnIndex = 0;
        } else {
            this.highestColumnIndex = 0;
            this.lowestColumnIndex = 100_000;
            for (IReadTimeblock timeblock : this.listSelection) {
                this.rowIndex = timeblock.getDayIndex();
                this.highestColumnIndex = Math.max(this.highestColumnIndex,
                        timeblock.getColumnIndex());
                this.lowestColumnIndex = Math.min(this.lowestColumnIndex,
                        timeblock.getColumnIndex());
            }
        }
    }

}
