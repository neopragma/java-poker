package com.neopragma.poker;

/**
 * NullCard implementation of Card returns most benign values from public methods.<br/>
 * suit() returns Suit.NONE<br/>
 * rank() returns Rank.NONE<br/>
 * isOneEyed() returns false<br/>
 * against() returns RelativeValue.SAME<br>
 *     <br/>
 * @author neopragma
 * @since 1.8
 */
public class NullCard implements Card {

    @Override
    public Suit suit() {
        return Suit.NONE;
    }

    @Override
    public Rank rank() {
        return Rank.NONE;
    }

    @Override
    public boolean isOneEyed() {
        return false;
    }

    @Override
    public RelativeValue against(Card that) {
        return RelativeValue.SAME;
    }

    @Override
    public RelativeValue against(Card that, SuitRanking suitRanking) {
        return RelativeValue.SAME;
    }
}
