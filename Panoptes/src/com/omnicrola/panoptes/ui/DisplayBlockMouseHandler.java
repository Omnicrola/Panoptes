package com.omnicrola.panoptes.ui;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DisplayBlock;

public class DisplayBlockMouseHandler {

    public class MousePoint {

        private int x;
        private int y;

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    private final MousePoint currentLocation;
    private final MousePoint dragStart;
    private final MousePoint dragEnd;
    private final DisplayBlockModelPresenter displayBlockModelPresenter;
    private final DataController controller;

    public DisplayBlockMouseHandler(DisplayBlockModelPresenter displayBlockList,
            DataController controller) {
        this.displayBlockModelPresenter = displayBlockList;
        this.controller = controller;
        this.currentLocation = new MousePoint();
        this.dragStart = new MousePoint();
        this.dragEnd = new MousePoint();
    }

    public MousePoint getCurrentMouseLocation() {
        return this.currentLocation;
    }

    public void loadSelectionAt(int screenX, int screenY) {
        int columnIndex = screenX / DisplayBlock.WIDTH;
        int rowIndex = screenY / DisplayBlock.HEIGHT;
        TimeblockSet selection = this.controller.getTimeBlock(rowIndex, columnIndex)
                .getParentSelection();
        this.displayBlockModelPresenter.setSelection(selection);
    }

    public void mouseMoved(int screenX, int screenY) {
        int columnIndex = screenX / DisplayBlock.WIDTH;
        int rowIndex = screenY / DisplayBlock.HEIGHT;

        this.currentLocation.x = screenX;
        this.currentLocation.y = screenY;
        this.displayBlockModelPresenter.setHighlightedBlock(rowIndex, columnIndex);

    }

    public void setDragEnd(int x, int y) {
        this.dragEnd.x = x;
        int x1 = this.dragStart.getX() / DisplayBlock.WIDTH;
        int y1 = this.dragStart.getY() / DisplayBlock.HEIGHT;
        int x2 = this.dragEnd.getX() / DisplayBlock.WIDTH;
        int y2 = this.dragEnd.getY() / DisplayBlock.HEIGHT;
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        TimeblockSet timeblockSelection = this.controller.createTimeblockSet(y1, x1, x2);
        this.displayBlockModelPresenter.setSelection(timeblockSelection);
    }

    public void setDragStart(int x, int y) {
        this.dragStart.x = x;
        this.dragEnd.y = y;
        this.dragStart.y = y;
    }
}
