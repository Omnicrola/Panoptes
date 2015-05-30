package com.omnicrola.panoptes.ui.autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AutoCompleteOptionFilter {

    private final int maxOptions;
    private final IOptionProvider provider;
    private final boolean caseSensitive;
    private final AlphabeticalComparator<Object> alphabeticalComparator;

    public AutoCompleteOptionFilter(IOptionProvider provider, int maxOptions,
            boolean isCaseSensitive) {
        this.provider = provider;
        this.maxOptions = maxOptions;
        this.caseSensitive = isCaseSensitive;
        this.alphabeticalComparator = new AlphabeticalComparator<Object>(isCaseSensitive);

    }

    private void alphabetizeList(List<Object> possibleOptions) {
        Collections.sort(possibleOptions, this.alphabeticalComparator);

    }

    public List<String> findOptions(String text) {
        if (!this.caseSensitive) {
            text = text.toLowerCase();
        }
        List<Object> possibleOptions = this.provider.getOptionsList();
        alphabetizeList(possibleOptions);
        Iterator<Object> iterator = possibleOptions.iterator();
        List<String> results = new ArrayList<>();
        int count = 0;
        while (iterator.hasNext() && count < this.maxOptions) {
            Object option = iterator.next();
            String objectDescription = option.toString();
            if (!this.caseSensitive) {
                objectDescription = objectDescription.toLowerCase();
            }
            if (objectDescription.startsWith(text)) {
                results.add(option.toString());
            }
        }
        return results;
    }

}
