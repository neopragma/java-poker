package com.neopragma.poker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {

    private Hand hand;
    private Card card1 = new PlayingCard(Suit.SPADES, Rank.ACE);
    private Card card2 = new PlayingCard(Suit.DIAMONDS, Rank.TWO);

    @Before
    public void beforeEach() {
        hand = new Hand();
        hand.add(card1);
        hand.add(card2);
    }

    @Test
    public void it_accepts_cards() {
        assertEquals(2, hand.show().size());
    }

    @Test
    public void it_removes_cards() {
        hand.remove(card1);
        assertEquals(1, hand.show().size());
    }
}
