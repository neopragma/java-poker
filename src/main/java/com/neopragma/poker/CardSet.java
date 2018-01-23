package com.neopragma.poker;

import java.util.List;

/**
 * A meaningful set of cards in a Hand.
 * @see Group
 * @author neopragma
 * @since 1.8
 */
public class CardSet {

    private List<Card> cards;

    public CardSet(List<Card> cards) {
        if (null == cards || cards.size() < 2) {
            //TODO replace string literal message with resource bundle reference
            throw new IllegalArgumentException("CardSet must contain at least 2 cards.");
        }
        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }

}
