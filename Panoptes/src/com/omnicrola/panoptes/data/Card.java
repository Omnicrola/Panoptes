package com.omnicrola.panoptes.data;

public class Card {

    public static final Card NULL = new Card("NULL");
    private final String cardID;

    public Card(String cardID) {
        this.cardID = cardID;
    }

    public String getCardID() {
        return this.cardID;
    }
}
