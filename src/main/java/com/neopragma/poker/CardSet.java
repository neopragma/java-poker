package com.neopragma.poker;

import java.util.List;

public interface CardSet extends Comparable<CardSet> {
    List<Card> cards();
    CardSet withSuitRanking(SuitRanking suitRanking);
    boolean suitRankingIsInEffect();
}
