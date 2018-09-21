package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

public abstract class AbstractCard implements Card {

    protected Rank rank;
    protected Suit suit;

    @Override
    public Suit suit() {
        return suit;
    }

    @Override
    public Rank rank() {
        return rank;
    }

    @Override
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
        if (!Card.class.isAssignableFrom(that.getClass())) {
            return false;
        }
        return (this.rank() == ((Card) that).rank() && this.suit() == ((Card) that).suit());
    }

    @Override
    public int compareTo(Card that) {
        if (null == that) {
            return 1;
        }
        if (this == that) {
            return 0;
        }
        if (this.rank().ordinal() > that.rank().ordinal()) {
            return 1;
        }
        if (this.rank().ordinal() < that.rank().ordinal()) {
            return -1;
        }
        return 0;
    }

    @Override
    public RelativeValue against(Card that) {
        Precondition.assertThat(null != that, new MessageId("M001"), "that");
        if (this.rank().ordinal() > that.rank().ordinal()) {
            return RelativeValue.HIGHER;
        }
        if (this.rank().ordinal() < that.rank().ordinal()) {
            return RelativeValue.LOWER;
        }
        return RelativeValue.SAME;
    }

    @Override
    public RelativeValue against(Card that, SuitRanking suitRanking) {
        Precondition.assertThat(null != that, new MessageId("M001"), "that");
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
