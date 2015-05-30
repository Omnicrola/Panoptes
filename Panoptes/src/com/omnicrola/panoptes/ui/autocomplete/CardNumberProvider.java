package com.omnicrola.panoptes.ui.autocomplete;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.util.ConstructorParameter;

public class CardNumberProvider implements IOptionProvider, IControlObserver {

	@ConstructorParameter("dataController")
	private final DataController dataController;
	private Set<String> allCardNumbers;

	public CardNumberProvider(DataController dataController) {
		this.dataController = dataController;
		this.allCardNumbers = new HashSet<String>();
	}

	@Override
	public void currentFilenameChanged(String filename) {
	}

	@Override
	public void dataChanged() {
		updateCardNumbers();
	}

	@Override
	public void timeblockSetChanged(TimeblockSet timeblockSet) {
		updateCardNumbers();
	}

	@Override
	public List<Object> getOptionsList() {
		return new ArrayList<Object>(this.allCardNumbers);
	}

	public void updateCardNumbers() {
		TimeblockSet allTimeblocks = this.dataController.getAllTimeblocks();
		this.allCardNumbers.clear();
		this.allCardNumbers = allTimeblocks.getBlockSet().stream().map(b -> b.getTimeData().getCard())
				.collect(Collectors.toSet());

	}
}
