package com.neopragma.poker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/** Checks of AbstractCard functionality to recognize one-eyed face cards.
 * @author neopragma
 * @since 1.8
 */
@RunWith(Parameterized.class)
public class Card_OneEyedTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new PlayingCard(Suit.SPADES, Rank.JACK),   true },
                { new PlayingCard(Suit.HEARTS, Rank.JACK),   true },
                { new PlayingCard(Suit.DIAMONDS, Rank.KING), true },
                { new PlayingCard(Suit.CLUBS, Rank.JACK),    false}
        });
    }

    private Card oneEyedInput;
    private boolean oneEyedExpected;
    public Card_OneEyedTest(Card input, boolean expected) {
        oneEyedInput = input;
        oneEyedExpected = expected;
    }

    @Test
    public void it_identifies_one_eyed_face_cards() {
        assertEquals(oneEyedExpected, oneEyedInput.isOneEyed());
    }

}
