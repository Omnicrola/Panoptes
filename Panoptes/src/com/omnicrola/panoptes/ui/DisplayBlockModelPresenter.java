package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omnicrola.panoptes.control.TimeblockIndexIterator;
import com.omnicrola.panoptes.control.TimeblockIndexIterator.IndexTuple;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DisplayBlock;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.ui.listener.IDisplayBlockModelObserver;

public class DisplayBlockModelPresenter {

    public class DisplayBlockObserverProxy {

        public void notifyDisplayBlockObservers() {
            updateObservers();
        }
    }

    private final List<List<DisplayBlock>> blockList;
    private final List<IDisplayBlockModelObserver> observerList;
    private final Map<String, Color> colorMap;
    private final ColorProvider colorProvider;
    private final DisplayBlockFlashGenerator flashGenerator;
    private DisplayBlock lastHighlightedBlock;
    private final DisplayBlockObserverProxy observerProxy;

    public DisplayBlockModelPresenter(List<List<DisplayBlock>> blockList,
            ColorProvider colorProvider, DisplayBlockFlashGenerator flashGenerator) {
        this.blockList = blockList;
        this.colorProvider = colorProvider;
        this.flashGenerator = flashGenerator;
        this.observerList = new ArrayList<>();
        this.colorMap = new HashMap<>();

        this.observerProxy = new DisplayBlockObserverProxy();
        this.lastHighlightedBlock = DisplayBlock.NULL;
    }

    public void addObserver(IDisplayBlockModelObserver observer) {
        this.observerList.add(observer);
    }

    public void clearSelection() {
        unselectedAllBlocks();
        updateSelectionObservers(TimeblockSet.EMPTY);
    }

    private DisplayBlock get(int x, int y) {
        if (x < this.blockList.size() && x >= 0) {
            if (y < this.blockList.get(x).size() && y >= 0) {
                return this.blockList.get(x).get(y);
            }
        }
        return DisplayBlock.NULL;
    }

    private Color getColorForSelection(TimeData timeData) {
        String key = timeData.getCard() + timeData.getProject();
        if (this.colorMap.containsKey(key)) {
            return this.colorMap.get(key);
        } else {
            Color color = this.colorProvider.nextColor();
            this.colorMap.put(key, color);
            return color;
        }
    }

    public int getHeight() {
        return this.blockList.size() * DisplayBlock.HEIGHT;
    }

    public int getWidth() {
        return this.blockList.get(0).size() * DisplayBlock.WIDTH;
    }

    public void paint(Graphics graphics) {
        int marker = 0;
        for (List<DisplayBlock> blockRow : this.blockList) {
            for (DisplayBlock displayBlock : blockRow) {
                if (marker == 4) {
                    displayBlock.paintWithRightBorder(graphics);
                    marker = 0;
                } else {
                    displayBlock.paint(graphics);
                }
                marker++;
            }
        }
    }

    public void refreshBlocks(TimeblockSet blockSet) {
        List<IReadTimeblock> blockList = blockSet.getBlockSet();
        for (IReadTimeblock timeblock : blockList) {
            int columnIndex = timeblock.getColumnIndex();
            int rowIndex = timeblock.getDayIndex();
            DisplayBlock displayBlock = this.blockList.get(rowIndex).get(columnIndex);
            displayBlock.setOccupied(timeblock.isOccupied());

            Color colorForSelection = getColorForSelection(timeblock.getTimeData());
            this.flashGenerator.checkForColorChange(displayBlock, colorForSelection,
                    this.observerProxy);
            displayBlock.setOccupiedColor(colorForSelection);
        }
    }

    public void setHighlightedBlock(int x, int y) {
        this.lastHighlightedBlock.setHighlight(false);
        this.lastHighlightedBlock = get(x, y);
        this.lastHighlightedBlock.setHighlight(true);
        updateObservers();
    }

    public void setSelection(TimeblockSet timeblockSelection) {
        unselectedAllBlocks();
        TimeblockIndexIterator indexIterator = timeblockSelection.indexIterator();
        while (indexIterator.hasNext()) {
            IndexTuple indexPair = indexIterator.next();
            DisplayBlock displayBlock = this.blockList.get(indexPair.getRow()).get(
                    indexPair.getColumn());
            displayBlock.setSelected(true);
        }
        updateSelectionObservers(timeblockSelection);
    }

    private void unselectedAllBlocks() {
        for (List<DisplayBlock> blockRow : this.blockList) {
            for (DisplayBlock displayBlock : blockRow) {
                displayBlock.setSelected(false);
            }
        }
    }

    private void updateObservers() {
        for (IDisplayBlockModelObserver observer : this.observerList) {
            observer.displayChanged();
        }
    }

    private void updateSelectionObservers(TimeblockSet newSet) {
        for (IDisplayBlockModelObserver observer : this.observerList) {
            observer.selectionChanged(newSet);
        }
    }

}
