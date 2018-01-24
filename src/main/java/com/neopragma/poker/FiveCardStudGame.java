package com.neopragma.poker;

import java.util.List;

public class FiveCardStudGame extends DefaultGame {

    Score score = null;

    public FiveCardStudGame() {
    }

    @Override
    public Result applyScoringRules(Hand hand1, Hand hand2) {
        List<Card> hand1Cards = hand1.show();
        hand1Cards.sort((Card hand1Cards1, Card hand1Cards2)->
                hand1Cards2.rank().ordinal()-hand1Cards1.rank().ordinal()
        );
        List<Card> hand2Cards = hand2.show();
        hand2Cards.sort((Card hand2Cards1, Card hand2Cards2)->
                hand2Cards2.rank().ordinal()-hand2Cards1.rank().ordinal()
        );

        Result result = Result.TIE;
        Score hand1Score = FiveCardStudScore.newInstance().score(hand1, this);
        Score hand2Score = FiveCardStudScore.newInstance().score(hand2, this);

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
            if (hand1Score.higherRank().ordinal() > hand2Score.higherRank().ordinal()) {
                result = Result.WIN;
            } else if (hand1Score.higherRank().ordinal() < hand2Score.higherRank().ordinal()) {
                result = Result.LOSE;

                //TODO break tie by checking the highest junk card in each hand

                } else if (suitsAreRanked()) {
                    if (suitBeatsSuit(hand1Cards.get(hand1Score.startingFrom()).suit(), hand2Cards.get(hand2Score.startingFrom()).suit()) == Result.WIN) {
                        result = Result.WIN;
                    } else {
                        result = Result.LOSE;
                    }
                }
        }
        return result;
    }
}
