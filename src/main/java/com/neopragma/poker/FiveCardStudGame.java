package com.neopragma.poker;

import java.util.List;

public class FiveCardStudGame extends DefaultGame {

    Score score = null;

    public FiveCardStudGame() {
    }

    @Override
    public Result applyScoringRules(Hand hand1, Hand hand2) {
        //TODO this method needs to be composed. It's hard to read.
        List<Card> hand1Cards = hand1.show();
        hand1Cards.sort((Card hand1Cards1, Card hand1Cards2) ->
                hand1Cards2.rank().ordinal() - hand1Cards1.rank().ordinal()
        );
        List<Card> hand2Cards = hand2.show();
        hand2Cards.sort((Card hand2Cards1, Card hand2Cards2) ->
                hand2Cards2.rank().ordinal() - hand2Cards1.rank().ordinal()
        );

        Result result = Result.TIE;
        Score hand1Score = FiveCardStudScore.newInstance().score(hand1);
        Score hand2Score = FiveCardStudScore.newInstance().score(hand2);

        //TODO remove display
        System.out.println("FiveCardStudGame.applyScoringRules");
        System.out.println("hand1: " + hand1Score.handValue() + ", hand2: " + hand2Score.handValue());


        if (hand1Score.handValue().numericalValue() > hand2Score.handValue().numericalValue()) {
            result = Result.WIN;
        } else if (hand1Score.handValue().numericalValue() < hand2Score.handValue().numericalValue()) {
            result = Result.LOSE;
        } else if (hand1Score.higherRank() != null && hand2Score.higherRank() != null) {
            if (hand1Score.higherRank().ordinal() > hand2Score.higherRank().ordinal()) {
                result = Result.WIN;
            } else if (hand1Score.higherRank().ordinal() < hand2Score.higherRank().ordinal()) {
                result = Result.LOSE;
            }
        }
        if (result == Result.TIE) {
            // break tie by higher-ranking card set
            if (hand1Score.higherRank().ordinal() > hand2Score.higherRank().ordinal()) {
                result = Result.WIN;
            } else if (hand1Score.higherRank().ordinal() < hand2Score.higherRank().ordinal()) {
                result = Result.LOSE;

            // break tie by highest ranking junk card if any
            } else if (hand1Score.highJunkCard().rank().ordinal() > hand2Score.highJunkCard().rank().ordinal()) {
                result = Result.WIN;
            } else if (hand1Score.highJunkCard().rank().ordinal() < hand2Score.highJunkCard().rank().ordinal()) {
                result = Result.LOSE;

            // break tie by higher value suit if suit ranking is in effect for this game
            } else if (suitsAreRanked()) {
                Suit hand1Suit = hand1.show().get(0).suit();
                Suit hand2Suit = hand2.show().get(0).suit();
                return suitRanking.suitBeatsSuit(hand1Suit, hand2Suit);
            }
        }
        return result;
    }
}
