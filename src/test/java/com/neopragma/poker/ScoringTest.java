package com.neopragma.poker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoringTest implements TestConstants {

    @Test
    public void with_no_suit_ranking_all_suits_compare_equal() {
        SuitRanking suitRanking = new SuitRanking();
        assertEquals(Result.TIE, suitRanking.suitBeatsSuit(Suit.CLUBS, Suit.DIAMONDS));
    }

    @Test
    public void with_suit_ranking_the_higher_suit_beats_the_lower_suit() {
        SuitRanking suitRanking = new SuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
        assertEquals(Result.WIN, suitRanking.suitBeatsSuit(Suit.DIAMONDS, Suit.CLUBS));
    }

    @Test
    public void with_suit_ranking_the_lower_suit_loses_to_the_higher_suit() {
        SuitRanking suitRanking = new SuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
        assertEquals(Result.LOSE, suitRanking.suitBeatsSuit(Suit.HEARTS, Suit.SPADES));
    }

    @Test
    public void with_no_suit_ranking_in_effect_two_straight_flushes_are_a_tie() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.ACE),
                new CardImpl(Suit.SPADES, Rank.KING),
                new CardImpl(Suit.SPADES, Rank.QUEEN),
                new CardImpl(Suit.SPADES, Rank.JACK),
                new CardImpl(Suit.SPADES, Rank.TEN));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.ACE),
                new CardImpl(Suit.HEARTS, Rank.QUEEN),
                new CardImpl(Suit.HEARTS, Rank.JACK),
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.HEARTS, Rank.KING));
        assertEquals(Result.TIE, hand1.beats(hand2, game));
    }

    @Test
    public void with_suit_ranking_in_effect_the_straight_flush_in_the_higher_suit_wins() {
        Game game = new FiveCardStudGame()
            .withSuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.ACE),
                new CardImpl(Suit.SPADES, Rank.KING),
                new CardImpl(Suit.SPADES, Rank.QUEEN),
                new CardImpl(Suit.SPADES, Rank.JACK),
                new CardImpl(Suit.SPADES, Rank.TEN));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.ACE),
                new CardImpl(Suit.HEARTS, Rank.QUEEN),
                new CardImpl(Suit.HEARTS, Rank.JACK),
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.HEARTS, Rank.KING));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }


    @Test
    public void with_no_suit_ranking_in_effect_the_higher_straight_flush_wins() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.ACE),
                new CardImpl(Suit.SPADES, Rank.KING),
                new CardImpl(Suit.SPADES, Rank.QUEEN),
                new CardImpl(Suit.SPADES, Rank.JACK),
                new CardImpl(Suit.SPADES, Rank.TEN));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.NINE),
                new CardImpl(Suit.HEARTS, Rank.QUEEN),
                new CardImpl(Suit.HEARTS, Rank.JACK),
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.HEARTS, Rank.KING));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }

    @Test
    public void with_suit_ranking_in_effect_the_higher_straight_flush_wins() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.NINE),
                new CardImpl(Suit.SPADES, Rank.KING),
                new CardImpl(Suit.SPADES, Rank.QUEEN),
                new CardImpl(Suit.SPADES, Rank.JACK),
                new CardImpl(Suit.SPADES, Rank.TEN));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.ACE),
                new CardImpl(Suit.HEARTS, Rank.QUEEN),
                new CardImpl(Suit.HEARTS, Rank.JACK),
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.HEARTS, Rank.KING));
        assertEquals(Result.LOSE, hand1.beats(hand2, game));
    }

    @Test
    public void straight_flush_beats_four_of_a_kind() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.SIX),
                new CardImpl(Suit.SPADES, Rank.FOUR),
                new CardImpl(Suit.SPADES, Rank.FIVE),
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.SPADES, Rank.TWO));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.ACE),
                new CardImpl(Suit.DIAMONDS, Rank.ACE),
                new CardImpl(Suit.CLUBS, Rank.ACE),
                new CardImpl(Suit.HEARTS, Rank.ACE),
                new CardImpl(Suit.SPADES, Rank.TEN));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }

    @Test
    public void the_higher_ranking_four_of_a_kind_wins() {
        Game game = new FiveCardStudGame()
                .withSuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.FIVE),
                new CardImpl(Suit.HEARTS, Rank.FIVE),
                new CardImpl(Suit.DIAMONDS, Rank.FIVE),
                new CardImpl(Suit.CLUBS, Rank.FIVE),
                new CardImpl(Suit.SPADES, Rank.ACE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.SPADES, Rank.SIX),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.SIX),
                new CardImpl(Suit.CLUBS, Rank.SIX),
                new CardImpl(Suit.HEARTS, Rank.ACE));
        assertEquals(Result.LOSE, hand1.beats(hand2, game));
    }

    @Test
    public void four_of_a_kind_beats_a_full_house() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.SIX),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.FOUR),
                new CardImpl(Suit.DIAMONDS, Rank.SIX));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TWO),
                new CardImpl(Suit.DIAMONDS, Rank.TWO),
                new CardImpl(Suit.CLUBS, Rank.TWO),
                new CardImpl(Suit.HEARTS, Rank.TWO),
                new CardImpl(Suit.SPADES, Rank.TEN));
        assertEquals(Result.LOSE, hand1.beats(hand2, game));
    }

    @Test
    public void a_full_house_beats_a_flush() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.SIX),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.FOUR),
                new CardImpl(Suit.DIAMONDS, Rank.SIX));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TWO),
                new CardImpl(Suit.HEARTS, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.FIVE),
                new CardImpl(Suit.HEARTS, Rank.TEN));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }

    @Test
    public void a_flush_beats_a_straight() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.FIVE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TWO),
                new CardImpl(Suit.HEARTS, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.FIVE),
                new CardImpl(Suit.HEARTS, Rank.TEN));
        assertEquals(Result.LOSE, hand1.beats(hand2, game));
    }

    @Test
    public void a_flush_with_a_better_high_card_beats_another_flush() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.SPADES, Rank.FOUR),
                new CardImpl(Suit.SPADES, Rank.SIX),
                new CardImpl(Suit.SPADES, Rank.SEVEN),
                new CardImpl(Suit.SPADES, Rank.NINE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TWO),
                new CardImpl(Suit.HEARTS, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.FIVE),
                new CardImpl(Suit.HEARTS, Rank.TEN));
        assertEquals(Result.LOSE, hand1.beats(hand2, game));
    }

    @Test
    public void a_straight_beats_three_of_a_kind() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.FIVE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.KING),
                new CardImpl(Suit.DIAMONDS, Rank.KING),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.FIVE),
                new CardImpl(Suit.SPADES, Rank.KING));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }

    @Test
    public void a_straight_with_a_better_high_card_beats_another_straight() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.FIVE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.DIAMONDS, Rank.JACK),
                new CardImpl(Suit.CLUBS, Rank.NINE),
                new CardImpl(Suit.HEARTS, Rank.EIGHT),
                new CardImpl(Suit.SPADES, Rank.SEVEN));
        assertEquals(Result.LOSE, hand1.beats(hand2, game));
    }

    @Test
    public void three_of_a_kind_beats_two_pair() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.SIX),
                new CardImpl(Suit.DIAMONDS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.THREE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.DIAMONDS, Rank.TEN),
                new CardImpl(Suit.CLUBS, Rank.NINE),
                new CardImpl(Suit.HEARTS, Rank.FOUR),
                new CardImpl(Suit.SPADES, Rank.NINE));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }

    @Test
    public void two_pair_beats_a_pair() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.KING),
                new CardImpl(Suit.HEARTS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.THREE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.TEN),
                new CardImpl(Suit.DIAMONDS, Rank.EIGHT),
                new CardImpl(Suit.CLUBS, Rank.NINE),
                new CardImpl(Suit.HEARTS, Rank.EIGHT),
                new CardImpl(Suit.SPADES, Rank.QUEEN));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }

    @Test
    public void two_pair_with_a_better_junk_card_beats_a_matching_pair() {
        Game game = new FiveCardStudGame();
        Hand hand1 = new Hand(
                new CardImpl(Suit.SPADES, Rank.THREE),
                new CardImpl(Suit.HEARTS, Rank.KING),
                new CardImpl(Suit.HEARTS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.SEVEN),
                new CardImpl(Suit.DIAMONDS, Rank.THREE));
        Hand hand2 = new Hand(
                new CardImpl(Suit.HEARTS, Rank.THREE),
                new CardImpl(Suit.SPADES, Rank.SEVEN),
                new CardImpl(Suit.CLUBS, Rank.THREE),
                new CardImpl(Suit.CLUBS, Rank.SEVEN),
                new CardImpl(Suit.SPADES, Rank.QUEEN));
        assertEquals(Result.WIN, hand1.beats(hand2, game));
    }
}
