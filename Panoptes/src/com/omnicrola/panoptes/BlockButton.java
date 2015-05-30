package com.omnicrola.panoptes;

import java.awt.Color;

import javax.swing.JButton;

public class BlockButton extends JButton {

    private static final long serialVersionUID = 3099220340048562602L;
    public static final Color DEFAULT_COLOR = Color.WHITE;
    public static final Color ACTIVE_COLOR = Color.WHITE;
    public static final Color SELECTED_COLOR = Color.WHITE;

    public BlockButton() {
        this.setBackground(DEFAULT_COLOR);
    }
}
