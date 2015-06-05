package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.omnicrola.panoptes.data.DisplayBlock;
import com.omnicrola.panoptes.settings.AppSettings;

public class VerticalAxisPanel extends JPanel {

    private static final int PADDING_X = 4;
    private static final int PADDING_Y = DisplayBlock.HEIGHT / 2;

    private final AppSettings settings;

    private static final long serialVersionUID = -2295587438960726028L;

    public VerticalAxisPanel(AppSettings settings) {
        this.settings = settings;
    }

    @Override
    public void paint(Graphics graphics) {
        clear(graphics);

        String[] days = this.settings.getDaysShort();
        int x = PADDING_X;
        int y = PADDING_Y;
        for (int i = 0; i < days.length; i++) {
            String day = days[i];
            y = i * DisplayBlock.HEIGHT + PADDING_Y;
            graphics.drawString(day, x, y);
        }
    }

    private void clear(Graphics graphics) {
        Font axisFont = this.settings.getAppFont(12, Font.BOLD);
        graphics.setFont(axisFont);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(Color.BLACK);
    }
}
