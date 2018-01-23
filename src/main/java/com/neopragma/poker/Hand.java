package com.neopragma.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a "hand" in a poker game. The number of cards in a hand and the meaningful combinations of cards vary by game.
 * @author neopragma
 */
public class Hand {

    private List<Card> cards;

    public Hand(Card...cards) {
        this.cards = new ArrayList<Card>(Arrays.asList(cards));
    }

    public List<Card> show() {
        return cards;
    }

    public Result beats(Hand other, Game game) {
        return game.applyScoringRules(this, other);
    }

}
