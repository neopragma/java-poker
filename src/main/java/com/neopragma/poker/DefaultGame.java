package com.neopragma.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultGame implements Game {

    protected String name;
    protected SuitRanking suitRanking = null;
    protected List<Card> wildCards;

    public Game withName(String name) {
        this.name = name;
        return this;
    }

    public Game withSuitRanking(Suit...suits) {
        suitRanking = new SuitRanking(suits);
        return this;
    }

    public Game withWildCards(Card...cards) {
        wildCards = new ArrayList<Card>(Arrays.asList(cards));
        return this;
    }

    @Override
    public boolean suitsAreRanked() {
        return null != suitRanking && suitRanking.suitsAreRanked();
    }

    @Override
    public Result suitBeatsSuit(Suit suit1, Suit suit2) {
        return suitRanking.suitBeatsSuit(suit1, suit2);
    }

    @Override
    public boolean wildCardsDeclared() {
        return null != wildCards && !wildCards.isEmpty();
    }

    @Override
    public List<Card> wildCards() {
        return wildCards;
    }

    @Override
    public Result applyScoringRules(Hand hand1, Hand hand2) {
        return Result.TIE;
    }

}
