package com.neopragma.poker;

import com.neopragma.preconditions.Precondition;

import java.util.List;

/**
 * A meaningful set of cards in a Hand.
 * @see Group
 * @author neopragma
 * @since 1.8
 */
public class CardSetImpl implements CardSet {

    private List<Card> cards;

    public CardSetImpl(List<Card> cards) {
        Precondition.assertThat((null != cards && cards.size() > 1), "M002");
        this.cards = cards;
    }

    @Override
    public List<Card> cards() {
        return cards;
    }

}
