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

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public Hand(Card...cards) {
        this(new ArrayList<>(Arrays.asList(cards)));
    }

    public List<Card> show() {
        return cards;
    }

    public Result beats(Hand other, Game game) {
        return game.applyScoringRules(this, other);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public void sort() {
        cards.sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );
    }
}
