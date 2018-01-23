package com.neopragma.poker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CardSetTest {

    @Test(expected=IllegalArgumentException.class)
    public void CardSet_throws_IllegalArgumentException_when_passed_a_null_reference() {
        new CardSet(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void CardSet_throws_IllegalArgumentException_when_passed_only_one_Card() {
        new CardSet(new ArrayList<Card>(Arrays.asList(new Card[] { new Card(Suit.SPADES, Rank.ACE) })));
    }

    @Test
    public void it_instantiates_valid_CardSet_object_with_two_Card_instances() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        CardSet set = new CardSet(cards);
        assertEquals(new Card(Suit.SPADES, Rank.JACK), set.cards().get(1));
    }
}
