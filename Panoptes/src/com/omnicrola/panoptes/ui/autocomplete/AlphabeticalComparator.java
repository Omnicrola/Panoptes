package com.omnicrola.panoptes.ui.autocomplete;

import java.util.Comparator;

public class AlphabeticalComparator<T> implements Comparator<T> {

    private final boolean caseSensitive;

    public AlphabeticalComparator(boolean isCaseSensitive) {
        this.caseSensitive = isCaseSensitive;
    }

    @Override
    public int compare(T first, T second) {
        if (this.caseSensitive) {
            return first.toString().compareTo(second.toString());
        } else {
            return first.toString().toLowerCase().compareTo(second.toString().toLowerCase());
        }
    }

}
