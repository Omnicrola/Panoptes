package com.omnicrola.panoptes.ui;

import java.awt.Font;
import java.util.HashMap;

public class FontRepository {

    public static final FontRepository INSTANCE = new FontRepository();
    private final HashMap<Integer, Font> fontCache;

    private FontRepository() {
        this.fontCache = new HashMap<Integer, Font>();
    }

    public Font getFont(int size, int face) {
        int hash = size * 7 + face * 31;
        if (this.fontCache.containsKey(hash)) {
            return this.fontCache.get(hash);
        }
        Font newFont = new Font(Font.SANS_SERIF, face, size);
        this.fontCache.put(hash, newFont);

        return newFont;
    }
}
