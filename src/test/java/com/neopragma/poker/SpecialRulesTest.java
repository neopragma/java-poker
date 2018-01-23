package com.neopragma.poker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Poker game special rules
 */
public class SpecialRulesTest implements TestConstants
{
    @Test
    public void it_reports_that_suits_are_not_ranked() {
        Game game = new FiveCardStudGame();
        assertEquals(false, game.suitsAreRanked());
    }

    @Test
    public void it_reports_that_a_suit_ranking_is_in_effect() {
        Game game = new FiveCardStudGame()
            .withSuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
        assertEquals(true, game.suitsAreRanked());
    }

    @Test
    public void it_reports_wild_cards_are_not_in_effect() {
        Game game = new FiveCardStudGame()
            .withSuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
        assertEquals(false, game.wildCardsDeclared());
    }

    @Test
    public void it_reports_wild_cards_are_in_effect() {
        Game game = new FiveCardStudGame()
            .withWildCards(new CardImpl(Suit.NONE, Rank.JOKER));
        assertEquals(true, game.wildCardsDeclared());
    }
}
