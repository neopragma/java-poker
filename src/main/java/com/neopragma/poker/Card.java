package com.neopragma.poker;

import com.neopragma.preconditions.Precondition;

import java.lang.reflect.Constructor;

public class Card {

    public static final String ARGUMENT_THAT_CANNOT_BE_NULL = "argument 'that' cannot be null.";
    public static final String CARD_MUST_HAVE_A_SUIT_AND_RANK = "Card must have a Suit and Rank.";
    private Rank rank;
    private Suit suit;

    Card(Suit suit, Rank rank) {
        Precondition.assertThat(null != suit && null != rank, CARD_MUST_HAVE_A_SUIT_AND_RANK);
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

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object that) {
        if (null == that) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (!that.getClass().isAssignableFrom(Card.class)) {
            return false;
        }
        return (this.rank() == ((Card) that).rank() && this.suit() == ((Card) that).suit());
    }

    public RelativeValue against(Card that) {
        Precondition.assertThat(null != that, "M001", "that");
        if (this.rank().ordinal() > that.rank().ordinal()) {
            return RelativeValue.HIGHER;
        }
        if (this.rank().ordinal() < that.rank().ordinal()) {
            return RelativeValue.LOWER;
        }
        return RelativeValue.SAME;
    }

    public RelativeValue against(Card that, SuitRanking suitRanking) {
        Precondition.assertThat(null != that, ARGUMENT_THAT_CANNOT_BE_NULL);
        if (null == suitRanking) {
            return against(that);
        }
        if (this.rank() != that.rank()) {
            return against(that);
        }
        switch (suitRanking.suitBeatsSuit(this.suit(), that.suit())) {
            case WIN  : return RelativeValue.HIGHER;
            case LOSE : return RelativeValue.LOWER;
            default          : return RelativeValue.SAME;
        }
    }
}
