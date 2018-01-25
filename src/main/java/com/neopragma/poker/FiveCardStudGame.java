package com.neopragma.poker;

import java.util.List;

public class FiveCardStudGame extends DefaultGame {

    @Override
    public Result applyScoringRules(Hand hand1, Hand hand2) {
        sortHand(hand1);
        sortHand(hand2);

        Score hand1_Score = score(hand1);
        Score hand2_Score = score(hand2);

        // compare the numerical scores of the two hands
        // clear winner, like four-of-a-kind beats full house

        Result result = winnerByValue(hand1_Score, hand2_Score);
        if (Result.TIE != result) return result;

        // same score: see if one has a higher ranking set,
        // like queens over sixes vs. tens over nines

        result = winnerByHighSet(hand1_Score, hand2_Score);
        if (Result.TIE != result) return result;

        // break tie by highest ranking junk card if any

        result = winnerByHigherRankingJunkCard(hand1_Score, hand2_Score);
        if (Result.TIE != result) return result;

        // break tie by higher value suit if suit ranking is in effect for this game

        result = winnerByHighestSuit(hand1, hand2);
        return result;
    }

    private void sortHand(Hand hand) {
        List<Card> cards = hand.show();
        cards.sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );
    }

    private Score score(Hand hand) {
        return FiveCardStudScore.newInstance().score(hand);
    }

    private Result winnerByValue(Score hand1_Score, Score hand2_Score) {
        int hand1_Value = numericalValue(hand1_Score);
        int hand2_Value = numericalValue(hand2_Score);
        if (hand1_Value > hand2_Value) {
            return Result.WIN;
        } else if (hand1_Value < hand2_Value) {
            return Result.LOSE;
        }
        return Result.TIE;
    }

    private int numericalValue(Score score) {
        return score.handValue().numericalValue();
    }

    private Result winnerByHighSet(Score hand1_Score, Score hand2_Score) {
        if (hand1_Score.higherRank() != null && hand2_Score.higherRank() != null) {
            if (hand1_Score.higherRank().ordinal() > hand2_Score.higherRank().ordinal()) {
                return Result.WIN;
            } else if (hand1_Score.higherRank().ordinal() < hand2_Score.higherRank().ordinal()) {
                return Result.LOSE;
            }
        }
        return Result.TIE;
    }

    private Result winnerByHigherRankingJunkCard(Score hand1_Score, Score hand2_Score) {
        if (hand1_Score.highJunkCard().rank().ordinal() > hand2_Score.highJunkCard().rank().ordinal()) {
            return Result.WIN;
        } else if (hand1_Score.highJunkCard().rank().ordinal() < hand2_Score.highJunkCard().rank().ordinal()) {
            return Result.LOSE;
        }
        return Result.TIE;
    }

    private Result winnerByHighestSuit(Hand hand1, Hand hand2) {

        if (suitsAreRanked()) {
            Suit hand1Suit = hand1.show().get(0).suit();
            Suit hand2Suit = hand2.show().get(0).suit();
            return suitRanking.suitBeatsSuit(hand1Suit, hand2Suit);
        }
        return Result.TIE;
    }

}
