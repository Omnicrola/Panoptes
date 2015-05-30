package com.omnicrola.panoptes.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.List;

public class SimpleFocusTraversalPolicy extends FocusTraversalPolicy {

    private final Component[] componentArray;

    public SimpleFocusTraversalPolicy(List<Component> componentList) {
        this.componentArray = new Component[componentList.size()];
        componentList.toArray(this.componentArray);
    }

    private Component findComponentWithOffset(Component component, int offset) {
        for (int i = 0; i < this.componentArray.length; i++) {
            if (this.componentArray[i].equals(component)) {
                int x = i + offset;
                x = x % this.componentArray.length;
                return this.componentArray[x];
            }
        }
        return this.componentArray[0];
    }

    @Override
    public Component getComponentAfter(Container arg0, Component component) {
        return findComponentWithOffset(component, +1);
    }

    @Override
    public Component getComponentBefore(Container arg0, Component component) {
        return findComponentWithOffset(component, -1);
    }

    @Override
    public Component getDefaultComponent(Container arg0) {
        return this.componentArray[0];
    }

    @Override
    public Component getFirstComponent(Container arg0) {
        return this.componentArray[0];
    }

    @Override
    public Component getLastComponent(Container arg0) {
        return this.componentArray[this.componentArray.length - 1];
    }

}
