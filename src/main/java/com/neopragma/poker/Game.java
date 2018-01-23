package com.neopragma.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Game {

    Result applyScoringRules(Hand hand1, Hand hand2);
    boolean suitsAreRanked();
    Result suitBeatsSuit(Suit suit1, Suit suit2);
    boolean wildCardsDeclared();
    List<Card> wildCards();

}
