package com.neopragma.poker;

import java.util.*;

/**
 * Represents the domain concept of a 'deck of cards'.
 * Deck instances are mutable. The cards remaining will change
 * as cards are dealt.
 * @author neopragma
 * @since 1.8
 */
public interface Deck {

    DeckType type();
    List<Player> deal(List<Player> players, int count);
    List<Card> contents();
    List<Card> remaining();
    void shuffle();
}
