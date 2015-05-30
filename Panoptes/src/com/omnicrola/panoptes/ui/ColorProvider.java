package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColorProvider {

    private final List<Color> colorList;
    private int index;

    public ColorProvider() {
        this.colorList = new ArrayList<>();
        this.colorList.add(new Color(246, 150, 121)); // Pastel Red
        this.colorList.add(new Color(249, 173, 129)); // Pastel Red Orange
        this.colorList.add(new Color(253, 173, 129)); // Pastel Yellow Orange
        this.colorList.add(new Color(255, 247, 153)); // Pastel Yellow

        this.colorList.add(new Color(196, 223, 155)); // Pastel Pea Green
        this.colorList.add(new Color(163, 211, 156)); // Pastel Yellow Green
        this.colorList.add(new Color(130, 202, 156)); // Pastel Green
        this.colorList.add(new Color(122, 204, 200)); // Pastel Green Cyan

        this.colorList.add(new Color(109, 207, 246)); // Pastel Cyan
        this.colorList.add(new Color(125, 167, 217)); // Pastel Cyan Blue
        this.colorList.add(new Color(131, 147, 202)); // Pastel Blue
        this.colorList.add(new Color(135, 129, 189)); // Pastel Blue Violet

        this.colorList.add(new Color(161, 134, 190)); // Pastel Violet
        this.colorList.add(new Color(189, 140, 191)); // Pastel Violet Magenta
        this.colorList.add(new Color(244, 154, 193)); // Pastel Magenta
        this.colorList.add(new Color(245, 152, 157)); // Pastel Magenta Red

        Collections.shuffle(this.colorList);
    }

    public Color nextColor() {
        this.index++;
        if (this.index >= this.colorList.size()) {
            this.index = 0;
        }
        Color color = this.colorList.get(this.index);
        return color;
    }

}
