package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CardSet_InstantiationTest {

    @Test(expected=PreconditionNotMetException.class)
    public void CardSet_throws_PreconditionNotMetException_when_passed_a_null_reference() {
        new CardSetImpl(null);
    }

    @Test
    public void it_instantiates_valid_CardSet_object_with_two_Card_instances() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new PlayingCard(Suit.CLUBS, Rank.ACE));
        cards.add(new PlayingCard(Suit.SPADES, Rank.JACK));
        CardSet set = new CardSetImpl(cards);
        assertEquals(new PlayingCard(Suit.SPADES, Rank.JACK), set.cards().get(1));
    }

    @Test
    public void NullCardSet_returns_an_empty_list() {
        CardSet set = new NullCardSet();
        assertEquals(true, set.cards().isEmpty());
    }
}
