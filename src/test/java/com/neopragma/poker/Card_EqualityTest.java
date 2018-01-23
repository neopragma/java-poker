package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Checks of PlayingCard equals() functionality.
 * @author neopragma
 * @since 1.8
 */
public class Card_EqualityTest {

    private Card king_of_hearts = new PlayingCard(Suit.HEARTS, Rank.KING);
    private Card king_of_diamonds = new PlayingCard(Suit.DIAMONDS, Rank.KING);
    private Card ten_of_diamonds = new PlayingCard(Suit.DIAMONDS, Rank.TEN);
    private Card ten_of_diamonds_dup = new PlayingCard(Suit.DIAMONDS, Rank.TEN);
    private Card ten_of_hearts = new PlayingCard(Suit.HEARTS, Rank.TEN);

    @Test
    public void it_compares_unequal_to_null() {
        assertEquals(false, king_of_hearts.equals(null));
    }

    @Test
    public void it_compares_unequal_to_an_object_of_a_different_type() {
        assertEquals(false, king_of_hearts.equals("not-a-card-object"));
    }

    @Test
    public void it_compares_equal_to_itself() {
        assertEquals(true, king_of_hearts.equals(king_of_hearts));
    }

    @Test
    public void it_compares_equal_when_suit_and_rank_match() {
        assertEquals(true, ten_of_diamonds.equals(ten_of_diamonds_dup));
    }

    @Test
    public void it_compares_unequal_when_suit_does_not_match() {
        assertEquals(false, ten_of_diamonds.equals(ten_of_hearts));
    }

    @Test
    public void it_compares_unequal_when_rank_does_not_match() {
        assertEquals(false, king_of_diamonds.equals(king_of_hearts));
    }

    @Test
    public void two_NullCard_instances_compare_equal() {
        assertEquals(true, new NullCard().equals(new NullCard()));
    }

}
