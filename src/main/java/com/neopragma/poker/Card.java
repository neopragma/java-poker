package com.neopragma.poker;

public interface Card extends Comparable<Card> {
    Suit suit();

    Rank rank();

    boolean isOneEyed();

    RelativeValue against(Card that);

    RelativeValue against(Card that, SuitRanking suitRanking);
}
