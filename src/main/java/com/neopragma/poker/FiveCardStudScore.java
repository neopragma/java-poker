package com.neopragma.poker;

public class FiveCardStudScore implements Score {
    private HandValue handValue;
    private Group group;
    private Card highJunkCard;
    private Rank higherRank;
    private Rank lowerRank;

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
    public Rank lowerRank() {
        return lowerRank;
    }

    @Override
    public Card highJunkCard() {
        return highJunkCard;
    }

    @Override
    public Score score(Hand hand) {
        lookForStraightAndFlush(hand);
        if (handValue == HandValue.UNSCORED) {
            scoreGroup(findCardSets(hand));
        }
        return this;
    }

    private void scoreGroup(Group group) {
        int[] countsBySet = new int[] { 0, 0, 0 };
        for (int i = 0 ; i < 3 ; i++) {
            if (i < group.cardSets().size()) {
                countsBySet[i] = group.cardSets().get(i).cards().size();
            }
        }

        Rank firstCardSetRank = group.cardSets().get(0).cards().get(0).rank();
        Rank secondCardSetRank = group.cardSets().get(1).cards().get(0).rank();

        highJunkCard = new NullCard();
        if (countsBySet[0] == 4) {
            handValue = HandValue.FOUR_OF_A_KIND;
            higherRank = firstCardSetRank;
            lowerRank = Rank.NONE;
            highJunkCard = group.cardSets().get(group.cardSets().size() - 1).cards().get(0);
        } else if (countsBySet[0] == 3) {
            if (countsBySet[1] == 2) {
                handValue = HandValue.FULL_HOUSE;
                higherRank = firstCardSetRank;
                lowerRank = secondCardSetRank;
            } else {
                handValue = HandValue.THREE_OF_A_KIND;
                higherRank = firstCardSetRank;
                lowerRank = Rank.NONE;
                highJunkCard = group.cardSets().get(1).cards().get(0);
            }
        } else if (countsBySet[0] == 2) {
            if (countsBySet[1] == 2) {
                handValue = HandValue.TWO_PAIR;
                higherRank = firstCardSetRank;
                lowerRank = secondCardSetRank;
                highJunkCard = group.cardSets().get(2).cards().get(0);
            } else {
                handValue = HandValue.PAIR;
                higherRank = firstCardSetRank;
                lowerRank = Rank.NONE;
                highJunkCard = group.cardSets().get(1).cards().get(0);
            }
        } else {
            handValue = HandValue.JUNK;
            higherRank = Rank.NONE;
            lowerRank = Rank.NONE;
            highJunkCard = group.cardSets().get(0).cards().get(0);
        }

        //TODO remove display
        System.out.println("in FiveCardStudScore.scoreGroup(), countsBySet: " + countsBySet[0] + ", " + countsBySet[1] + ", " + countsBySet[2]);
        System.out.println("handValue: " + handValue);
        System.out.println("higherRank: " + higherRank);
        System.out.println("lowerRank: " + lowerRank);
        System.out.println("highJunkCard: " + highJunkCard.rank() + " of " + highJunkCard.suit());

    }

    private void lookForStraightAndFlush(Hand hand) {
        handValue = HandValue.UNSCORED;
        boolean flush = flush(hand);
        boolean straight = straight(hand);

        higherRank = hand.show().get(0).rank();
        lowerRank = Rank.NONE;
        highJunkCard = new NullCard();
        if (flush && straight) {
            if (higherRank == Rank.ACE) {
                handValue = HandValue.ROYAL_FLUSH;
            } else {
                handValue = HandValue.STRAIGHT_FLUSH;
            }
        } else if (flush) {
            handValue = HandValue.FLUSH;
            higherRank = hand.show().get(0).rank();
        } else if (straight) {
            handValue = HandValue.STRAIGHT;
            higherRank = highCardRank(hand);
        }
    }
}
