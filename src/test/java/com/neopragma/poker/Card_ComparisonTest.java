package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Checks of PlayingCard comparison functionality (this.against(that).
 * @author neopragma
 * @since 1.8
 */
public class Card_ComparisonTest {

    private Card king_of_hearts = new PlayingCard(Suit.HEARTS, Rank.KING);
    private Card king_of_diamonds = new PlayingCard(Suit.DIAMONDS, Rank.KING);
    private Card ten_of_diamonds = new PlayingCard(Suit.DIAMONDS, Rank.TEN);

    @Test
    public void it_requires_non_null_argument_when_comparing_card_values() {
        try {
            assertEquals(RelativeValue.HIGHER, king_of_hearts.against(null));
        } catch (PreconditionNotMetException expectedException) {
            assertEquals("In com.neopragma.poker.AbstractCard.against: Argument <that> cannot be null", expectedException.getMessage());
        } catch (Exception unexpectedException) {
            fail("Expected PreconditionNotMetException, got " + unexpectedException);
        }
    }

    @Test
    public void king_is_higher_rank_than_ten() {
        assertEquals(RelativeValue.HIGHER, king_of_hearts.against(ten_of_diamonds));
    }

    @Test
    public void ten_is_lower_rank_than_king() {
        assertEquals(RelativeValue.LOWER, ten_of_diamonds.against(king_of_hearts));
    }

    @Test
    public void two_kings_have_the_same_rank_value() {
        assertEquals(RelativeValue.SAME, king_of_diamonds.against(king_of_hearts));
    }

    @Test
    public void king_of_higher_suit_has_higher_value_than_king_of_lower_suit() {
        assertEquals(RelativeValue.HIGHER, king_of_hearts.against(king_of_diamonds, new SuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS)));
    }
}
