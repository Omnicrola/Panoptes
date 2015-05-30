package com.omnicrola.panoptes.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class SelfSortingComboBoxModel<E> implements ComboBoxModel<E> {

    private final List<E> dataList;
    private E selected;
    private final ArrayList<ListDataListener> listenerList;
    private final Comparator<E> comparator;

    public SelfSortingComboBoxModel(List<E> dataList, Comparator<E> comparator) {
        this.dataList = dataList;
        this.comparator = comparator;
        this.listenerList = new ArrayList<>();
        this.selected = dataList.get(0);
        selfSort();
    }

    public void addElement(E newElement) {
        this.dataList.add(newElement);
        selfSort();
        updateListeners();
    }

    @Override
    public void addListDataListener(ListDataListener listener) {
        this.listenerList.add(listener);
    }

    @Override
    public E getElementAt(int index) {
        return this.dataList.get(index);
    }

    @Override
    public E getSelectedItem() {
        return this.selected;
    }

    @Override
    public int getSize() {
        return this.dataList.size();
    }

    public void removeElement(E element) {
        this.dataList.remove(element);
        updateListeners();
    }

    public void removeElementByIndex(int index) {
        this.dataList.remove(index);
        updateListeners();
    }

    @Override
    public void removeListDataListener(ListDataListener listener) {
        this.listenerList.remove(listener);
    }

    private void selfSort() {
        Collections.sort(this.dataList, this.comparator);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (this.dataList.contains(anItem)) {
            this.selected = this.dataList.get(this.dataList.indexOf(anItem));
        }
    }

    private void updateListeners() {
        ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0);
        for (ListDataListener listener : this.listenerList) {
            listener.contentsChanged(event);
        }
    }
}
