package com.omnicrola.panoptes.bootstrap;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MenuItemFactory {

    public JMenuItem createMenuItemWithHotkey(String name, int hotkey, ActionListener actionCommand) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(actionCommand);
        menuItem.setMnemonic(hotkey);
        return menuItem;
    }

}
