package com.neopragma.poker;

public class FiveCardStudScore implements Score {
    private HandValue handValue;
    private Rank higherRank;
    private int higherCount;
    private Rank lowerRank;
    private int lowerCount;
    private int startingFrom;

    private FiveCardStudScore() {
    }

    public static Score newInstance() {
        return new FiveCardStudScore();
    }

    @Override
    public HandValue handValue() {
        return handValue;
    }

    @Override
    public Rank higherRank() {
        return higherRank;
    }

    @Override
    public int higherCount() {
        return higherCount;
    }

    @Override
    public Rank lowerRank() {
        return lowerRank;
    }

    @Override
    public int lowerCount() {
        return lowerCount;
    }

    @Override
    public int startingFrom() {
        return startingFrom;
    }

    @Override
    public Score score(Hand hand, Game game) {
        lookForStraightAndFlush(hand, game);
        if (handValue == HandValue.UNSCORED) {
            lookForRankGroups(hand, game);
        }
        return this;
    }

    private void lookForStraightAndFlush(Hand hand, Game game) {
        handValue = HandValue.UNSCORED;
        boolean flush = flush(hand, game);
        boolean straight = straight(hand, game);
        higherRank = highCardRank(hand, game, startingFrom);

        if (flush && straight) {
            startingFrom = 0;
            if (higherRank == Rank.ACE) {
                handValue = HandValue.ROYAL_FLUSH;
            } else {
                handValue = HandValue.STRAIGHT_FLUSH;
            }
        } else if (flush) {
            handValue = HandValue.FLUSH;
        } else if (straight) {
            handValue = HandValue.STRAIGHT;
        }
    }

    private void lookForRankGroups(Hand hand, Game game) {
        //TODO change return type to Group and adjust code in this method accordingly
        //GroupResult groupResult = groups(hand, game);
        GroupResult groupResult = null;
        groups(hand, game);

        System.out.println("In FiveCardStudScore.lookForRankGroups, GroupResult comes in with:");
        System.out.println("higherRank: " + groupResult.higherRank() + ", higherCount: " + groupResult.higherCount());

        higherRank = groupResult.higherRank();
        higherCount = groupResult.higherCount();
        lowerRank = groupResult.lowerRank();
        lowerCount = groupResult.lowerCount();
        if (higherCount == 4) {
            handValue = HandValue.FOUR_OF_A_KIND;
        } else if (higherCount == 3) {
            if (lowerCount < 2) {
                handValue = HandValue.THREE_OF_A_KIND;
            } else {
                handValue = HandValue.FULL_HOUSE;
            }
        } else if (higherCount == 2) {
            if (lowerCount == 2) {
                handValue = HandValue.TWO_PAIR;
            } else {
                handValue = HandValue.PAIR;
            }
        } else {
            handValue = HandValue.JUNK;
        }

        System.out.println("Just before returning the score:");
        System.out.println("handValue: " + handValue + ", higherRank: " + higherRank() + ", higherCount: " + higherCount());
    }

    @Override
    public Rank highCardRank(Hand hand, Game game, int startingFrom) {
        return hand.show().get(startingFrom).rank();
    }
}
