package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.data.DisplayBlock;

public class HorizontalAxisPanel extends JPanel {

    private static final long serialVersionUID = 8181987576265947961L;
    private static final int PADDING_Y = 16;
    private static final int PADDING_X = DisplayBlock.WIDTH;
    private final AppSettings settings;
    private int xOffset;

    public HorizontalAxisPanel(AppSettings settings) {
        this.settings = settings;
    }

    private void clear(Graphics graphics) {
        Font axisFont = this.settings.getAppFont(12, Font.BOLD);
        graphics.setFont(axisFont);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(Color.BLACK);
    }

    @Override
    public void paint(Graphics graphics) {
        clear(graphics);

        String[] timeIncrements = this.settings.getTimeIncrements();
        int blocksPerHour = this.settings.getBlocksPerHour();
        for (int i = 0; i < timeIncrements.length; i += blocksPerHour) {
            int x = i * DisplayBlock.WIDTH + PADDING_X + this.xOffset;
            int y = PADDING_Y;
            String time = timeIncrements[i];
            graphics.drawString(time, x, y);
        }
    }

    public void setOffsetX(int xOffset) {
        this.xOffset = xOffset;

    }
}
