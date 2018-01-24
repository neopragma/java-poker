package com.neopragma.poker;

import com.neopragma.preconditions.Precondition;

import java.util.ArrayList;
import java.util.List;

/**
 * A meaningful set of cards in a Hand.
 * @see Group
 * @author neopragma
 * @since 1.8
 */
public abstract class AbstractCardSet implements CardSet {

    protected List<Card> cards = new ArrayList<>();
    protected SuitRanking suitRanking;

    @Override
    public CardSet withSuitRanking(SuitRanking suitRanking) {
        this.suitRanking = suitRanking;
        return this;
    }

    @Override
    public boolean suitRankingIsInEffect() {
        return (null != suitRanking);
    }

    @Override
    public List<Card> cards() {
        return cards;
    }

    @Override
    public int compareTo(CardSet that) {
        int LOWER = -1;
        int SAME = 0;
        int HIGHER = 1;
        if (null == that) {
            return HIGHER;
        }
        if (this.cards().size() > that.cards().size()) {
            return HIGHER;
        }
        if (this.cards().size() < that.cards().size()) {
            return LOWER;
        }

        Card thisFirstCard = this.cards().size() > 0 ? this.cards().get(0) : null;
        Card thatFirstCard = that.cards().size() > 0 ? that.cards().get(0) : null;
        if (null == thisFirstCard) {
            if (null == thatFirstCard) {
                return SAME;
            } else {
                return LOWER;
            }
        } else {
            if (null == thatFirstCard) {
                return HIGHER;
            }
        }

        if (thisFirstCard.rank().ordinal() < thatFirstCard.rank().ordinal()) {
            return HIGHER;
        }
        if (thisFirstCard.rank().ordinal() < thatFirstCard.rank().ordinal()) {
            return LOWER;
        }
        if (suitRankingIsInEffect()) {
            if (Result.WIN == suitRanking.suitBeatsSuit(thisFirstCard.suit(), thatFirstCard.suit())) {
                return HIGHER;
            }
            if (Result.LOSE == suitRanking.suitBeatsSuit(thisFirstCard.suit(), thatFirstCard.suit())) {
                return LOWER;
            }
        }
        return SAME;
    }

}
