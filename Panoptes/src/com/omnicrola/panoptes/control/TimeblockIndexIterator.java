package com.omnicrola.panoptes.control;

import java.util.ArrayList;

import com.omnicrola.panoptes.data.IReadTimeblock;

public class TimeblockIndexIterator {

    public class IndexTuple {

        private int row;
        private int column;

        public int getColumn() {
            return this.column;
        }

        public int getRow() {
            return this.row;
        }
    }

    private final ArrayList<IReadTimeblock> blockList;
    private int index;
    private final IndexTuple indexTuple;

    public TimeblockIndexIterator(ArrayList<IReadTimeblock> arrayList) {
        this.blockList = arrayList;
        this.indexTuple = new IndexTuple();
        this.index = -1;
    }

    public boolean hasNext() {
        return this.index < this.blockList.size() - 1;
    }

    public IndexTuple next() {
        this.index++;
        this.indexTuple.row = this.blockList.get(this.index).getDayIndex();
        this.indexTuple.column = this.blockList.get(this.index).getColumnIndex();
        return this.indexTuple;
    }

}
