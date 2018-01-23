package com.neopragma.poker;

import com.neopragma.preconditions.Precondition;

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
        Precondition.assertThat((null != cards && cards.size() > 1), "M002");
        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }

}
