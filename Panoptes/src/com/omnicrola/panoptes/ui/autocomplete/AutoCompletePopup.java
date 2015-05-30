package com.omnicrola.panoptes.ui.autocomplete;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.omnicrola.panoptes.ui.listener.AutoCompleteSelectionListener;

public class AutoCompletePopup extends JPopupMenu {

    private static final long serialVersionUID = -4133312578259833218L;

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color SELECT_COLOR = Color.CYAN;

    private final List<JMenuItem> menuItems;
    private final JTextField jTextField;

    private int focusIndex;

    private final int maxOptions;

    protected AutoCompletePopup(int maxOptions, JTextField jTextField) {
        this.maxOptions = maxOptions;
        this.jTextField = jTextField;
        this.setBackground(DEFAULT_COLOR);
        this.menuItems = new ArrayList<JMenuItem>(maxOptions);
        for (int i = 0; i < maxOptions; i++) {
            JMenuItem jMenuItem = createMenuItem();
            this.menuItems.add(jMenuItem);
        }
    }

    private JMenuItem createMenuItem() {
        final JMenuItem jMenuItem = new JMenuItem();
        jMenuItem.setBackground(DEFAULT_COLOR);
        jMenuItem.addActionListener(new AutoCompleteSelectionListener(this, jMenuItem));
        return jMenuItem;
    }

    public void moveFocusDown() {
        if (this.focusIndex <= this.maxOptions) {
            this.focusIndex++;
        }
        setFocusHighlight();
    }

    public void moveFocusUp() {
        if (this.focusIndex >= 0) {
            this.focusIndex--;
        }
        setFocusHighlight();
    }

    private void resetOptions(List<String> options) {
        this.removeAll();
        int max = Math.min(options.size(), this.menuItems.size());
        for (int i = 0; i < max; i++) {
            String string = options.get(i);
            JMenuItem jMenuItem = this.menuItems.get(i);
            jMenuItem.setText(string);
            this.add(jMenuItem);
        }
        this.setVisible(true);
        Point locationOnScreen = this.jTextField.getLocationOnScreen();
        locationOnScreen.y += this.jTextField.getHeight() - 2;
        this.setLocation(locationOnScreen);
        this.repaint();
    }

    private void setFocusHighlight() {
        for (int i = 0; i < this.menuItems.size(); i++) {
            JMenuItem item = this.menuItems.get(i);
            Color color = (i == this.focusIndex) ? SELECT_COLOR : DEFAULT_COLOR;
            item.setBackground(color);
        }
    }

    public void setMainField(String text) {
        this.jTextField.setText(text);
        for (JMenuItem item : this.menuItems) {
            item.setBackground(DEFAULT_COLOR);
        }
        this.setVisible(false);
    }

    public void setOptions(List<String> options) {
        this.focusIndex = -1;
        if (options.isEmpty()) {
            this.setVisible(false);
        } else {
            resetOptions(options);
        }
    }

    public void updateTextFieldWithSelectedIndex() {
        int index = (this.focusIndex == -1) ? 0 : this.focusIndex;
        String text = this.menuItems.get(index).getText();
        this.jTextField.setText(text);
        this.focusIndex = -1;
        setFocusHighlight();
    }
}
