package com.neopragma.poker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CardSet_ComparisonTest implements TestConstants {

    private CardSet pair_of_nines_high = new CardSetImpl(new ArrayList<>(Arrays.asList(
            new PlayingCard(Suit.SPADES, Rank.NINE),
            new PlayingCard(Suit.HEARTS, Rank.NINE)
    )));

    private CardSet pair_of_nines_low = new CardSetImpl(new ArrayList<>(Arrays.asList(
            new PlayingCard(Suit.CLUBS, Rank.NINE),
            new PlayingCard(Suit.DIAMONDS, Rank.NINE)
    )));

    private CardSet three_sixes = new CardSetImpl(new ArrayList<>(Arrays.asList(
            new PlayingCard(Suit.CLUBS, Rank.SIX),
            new PlayingCard(Suit.HEARTS, Rank.SIX),
            new PlayingCard(Suit.DIAMONDS, Rank.SIX)
    )));

    private CardSet four_eights = new CardSetImpl(new ArrayList<>(Arrays.asList(
            new PlayingCard(Suit.CLUBS, Rank.EIGHT),
            new PlayingCard(Suit.HEARTS, Rank.EIGHT),
            new PlayingCard(Suit.SPADES, Rank.EIGHT),
            new PlayingCard(Suit.DIAMONDS, Rank.EIGHT)
    )));


    @Test
    public void cardsets_of_equal_size_and_rank_compare_equal() {
        assertEquals(EXPECT_SAME, SAME, pair_of_nines_high.compareTo(pair_of_nines_low));
    }

    @Test
    public void cardsets_of_equal_size_and_rank_distinguished_by_higher_suit_ranking() {
        CardSet nines_high = pair_of_nines_high.withSuitRanking(standardSuitRanking);
        CardSet nines_low = pair_of_nines_low.withSuitRanking(standardSuitRanking);
        assertEquals(EXPECT_HIGHER, HIGHER, pair_of_nines_high.compareTo(pair_of_nines_low));
    }

    @Test
    public void cardsets_of_equal_size_and_rank_distinguished_by_lower_suit_ranking() {
        CardSet nines_high = pair_of_nines_high.withSuitRanking(standardSuitRanking);
        CardSet nines_low = pair_of_nines_low.withSuitRanking(standardSuitRanking);
        assertEquals(EXPECT_LOWER, LOWER, pair_of_nines_low.compareTo(pair_of_nines_high));
    }

    @Test
    public void a_set_of_three_is_higher_than_a_set_of_two() {
        assertEquals(EXPECT_HIGHER, HIGHER, three_sixes.compareTo(pair_of_nines_high));
    }

    @Test
    public void a_set_of_two_is_lower_than_a_set_of_three() {
        assertEquals(EXPECT_LOWER, LOWER, pair_of_nines_low.compareTo(three_sixes));
    }

    @Test
    public void a_set_of_four_is_higher_than_a_set_of_three() {
        assertEquals(EXPECT_HIGHER, HIGHER, four_eights.compareTo(three_sixes));
    }

    @Test
    public void a_set_of_three_is_lower_than_a_set_of_four() {
        assertEquals(EXPECT_LOWER, LOWER, three_sixes.compareTo(four_eights));
    }

    @Test
    public void two_null_card_sets_compare_equal() {
        assertEquals(EXPECT_SAME, SAME, new NullCardSet().compareTo(new NullCardSet()));
    }

    @Test
    public void non_null_card_set_compares_higher_than_null_card_set() {
        assertEquals(EXPECT_HIGHER, HIGHER, pair_of_nines_high.compareTo(new NullCardSet()));
    }
    @Test
    public void null_card_set_compares_lower_than_non_null_card_set() {
        assertEquals(EXPECT_LOWER, LOWER, new NullCardSet().compareTo(pair_of_nines_high));
    }


}
