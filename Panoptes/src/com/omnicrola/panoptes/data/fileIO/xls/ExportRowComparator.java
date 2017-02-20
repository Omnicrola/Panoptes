package com.omnicrola.panoptes.data.fileIO.xls;

import java.util.Comparator;

public class ExportRowComparator implements Comparator<ExportDataRow> {

	@Override
	public int compare(ExportDataRow row1, ExportDataRow row2) {
		String project1 = row1.getWorkStatement().getProjectName();
		String project2 = row2.getWorkStatement().getProjectName();
		int projectCompare = project1.compareTo(project2);
		if (projectCompare == 0) {
			String role1 = row1.getRole();
			String role2 = row2.getRole();
			int roleCompare = role1.compareTo(role2);
			if (roleCompare == 0) {
				String card1 = row1.getCard();
				String card2 = row2.getCard();
				return compareCardNumbers(card1, card2);
			} else {
				return roleCompare;
			}
		} else {
			return projectCompare;
		}
	}

	private int compareCardNumbers(String card1, String card2) {
		return compareByCharacter(0, card1, card2);
	}

	private int compareByCharacter(int index, String card1, String card2) {
		if (card1.length() > index && card2.length() > index) {
			char character1 = card1.charAt(index);
			char character2 = card2.charAt(index);
			boolean firstCardHasLetter = Character.isLetter(character1);
			boolean secondCardIsLetter = Character.isLetter(character2);

			boolean bothCharactersAreLetters = firstCardHasLetter && secondCardIsLetter;
			boolean bothCharactersAreNotLetters = !firstCardHasLetter && !secondCardIsLetter;

			if (bothCharactersAreLetters || bothCharactersAreNotLetters) {
				int normalComparison = Character.compare(character1, character2);
				if (normalComparison == 0) {
					return compareByCharacter(index + 1, card1, card2);
				} else {
					return normalComparison;
				}
			} else if (firstCardHasLetter) {
				return -1;
			} else if (secondCardIsLetter) {
				return 1;
			} else {
				return compareByCharacter(index + 1, card1, card2);
			}
		}
		return 0;
	}
}