package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Checks of Card functionality to ensure invalid objects can't be instantiated.
 */
public class Card_InstantiationTest {

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_PreconditionNotMetException_when_Suit_is_null() {
        new CardImpl(null, Rank.JACK);
    }

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_PreconditionNotMetException_when_Rank_is_null() {
        new CardImpl(Suit.SPADES, null);
    }

    @Test
    public void it_instantiates_a_valid_Card_object() {
        assertEquals(Rank.TEN, new CardImpl(Suit.DIAMONDS, Rank.TEN).rank());
    }

    @Test
    public void it_instantiates_a_valid_NullCard_object() {
        assertEquals(Suit.NONE, new NullCard().suit());
    }

}
