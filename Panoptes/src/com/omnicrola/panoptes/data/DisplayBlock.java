package com.omnicrola.panoptes.data;

import java.awt.Color;
import java.awt.Graphics;

public class DisplayBlock {

    public static final DisplayBlock NULL = new DisplayBlock(-1, -1);
    private static final Color BORDER_COLOR = Color.GRAY;
    private static final Color DEFAULT_COLOR = new Color(240, 240, 240);

    public static final int WIDTH = 20;
    public static final int HEIGHT = 50;

    private boolean isHighlighted;
    private final int x;
    private final int y;

    private boolean isSelected;
    private final int rowIndex;
    private final int columnIndex;
    private boolean isOccupied;
    private Color occupiedColor;
    private boolean isFlashing;

    public DisplayBlock(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.x = columnIndex * WIDTH;
        this.y = rowIndex * HEIGHT;
        this.occupiedColor = Color.WHITE;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void paint(Graphics graphics) {
        Color color = pickColor();
        graphics.setColor(color);
        graphics.fillRect(this.x, this.y, WIDTH, HEIGHT);

        graphics.setColor(BORDER_COLOR);
        graphics.drawRect(this.x, this.y, WIDTH, HEIGHT);
    }

    public void paintWithRightBorder(Graphics graphics) {
        paint(graphics);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(this.x - 1, this.y, this.x - 1, this.y + HEIGHT);
        graphics.drawLine(this.x, this.y, this.x, this.y + HEIGHT);
    }

    private Color pickColor() {
        Color color = DEFAULT_COLOR;

        if (this.isOccupied) {
            color = this.occupiedColor;
        }
        if (this.isHighlighted) {
            color = Color.CYAN;
        }
        if (this.isSelected) {
            color = Color.YELLOW;
        }
        if (this.isFlashing) {
            color = Color.WHITE;
        }
        return color;
    }

    public void setFlashing(boolean isFlashing) {
        this.isFlashing = isFlashing;
    }

    public void setHighlight(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;

    }

    public void setOccupiedColor(Color colorForSelection) {
        this.occupiedColor = colorForSelection;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

}
