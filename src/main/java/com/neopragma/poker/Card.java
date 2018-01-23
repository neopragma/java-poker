package com.neopragma.poker;

import java.lang.reflect.Constructor;

public class Card {

    private Rank rank;
    private Suit suit;

    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;    }

    public Suit suit() {
        return suit;
    }

    public Rank rank() {
        return rank;
    }

    public boolean isOneEyed() {
        return (rank == Rank.JACK && suit == Suit.SPADES) ||
                (rank == Rank.JACK && suit == Suit.HEARTS) ||
                (rank == Rank.KING && suit == Suit.DIAMONDS);
    }

}
