package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeData;

public class MouseTip {

    private static final int PADDING = 5;
    private static final String EXTRA_MOUSETIP = "\nRight-click to select previous entry.";
    private static final int SPACING = 8;

    private String text = "";
    private int y;
    private int x;
    private final AppSettings settings;
    private final DataController controller;
    private boolean isOnLeftSide;
    private boolean isOnTopSide;

    public MouseTip(AppSettings settings, DataController controller) {
        this.settings = settings;
        this.controller = controller;
    }

    private void buildMousetipText(IReadTimeblock timeBlock) {
        String[] daysShort = this.settings.getDaysShort();

        int dayIndex = timeBlock.getDayIndex();
        int blockIndex = timeBlock.getColumnIndex();

        TimeData timeData = timeBlock.getTimeData();
        String project = timeData.getProject();
        String card = timeData.getCard();
        String startTime = this.settings.getTimeIncrements()[blockIndex];
        String endTime = this.settings.getTimeIncrements()[blockIndex + 1];

        this.text = daysShort[dayIndex] + " " + startTime + " - " + endTime + "\n" + project + " "
                + card + EXTRA_MOUSETIP;

    }

    private int findMaxLineWidth(Graphics graphics, String[] lines) {
        int width = 0;
        FontMetrics fontMetrics = graphics.getFontMetrics();
        for (int i = 0; i < lines.length; i++) {
            String string = lines[i];
            int lineWidth = fontMetrics.stringWidth(string);
            width = Math.max(width, lineWidth);
        }
        return width;
    }

    public String getText() {
        return this.text;
    }

    public void moveToLeftSide(boolean isOnLeftSide) {
        this.isOnLeftSide = isOnLeftSide;
    }

    public void moveToTopSide(boolean isOnTopSide) {
        this.isOnTopSide = isOnTopSide;
    }

    public void paint(Graphics graphics) {
        String[] lines = this.text.split("\n");

        int width = findMaxLineWidth(graphics, lines) + PADDING;
        int fontHeight = graphics.getFontMetrics().getHeight();
        int height = lines.length * fontHeight + PADDING;

        int drawX = (this.isOnLeftSide) ? this.x - width - SPACING : this.x + SPACING;
        int drawY = (this.isOnTopSide) ? this.y - height + SPACING : this.y;

        graphics.setColor(Color.WHITE);
        graphics.fillRect(drawX, drawY, width, height);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(drawX, drawY, width, height);

        int textX = drawX + PADDING / 2;
        int textY = drawY + fontHeight + PADDING / 2;
        for (int i = 0; i < lines.length; i++) {
            String string = lines[i];
            graphics.drawString(string, textX, textY + fontHeight * i);
        }
    }

    public void setPixelPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateBlockPosition(int rowIndex, int columnIndex) {
        IReadTimeblock timeBlock = this.controller.getTimeBlock(rowIndex, columnIndex);
        buildMousetipText(timeBlock);
    }

}
