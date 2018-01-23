package com.neopragma.poker;

public interface Card {
    Suit suit();

    Rank rank();

    boolean isOneEyed();

    RelativeValue against(Card that);

    RelativeValue against(Card that, SuitRanking suitRanking);
}
