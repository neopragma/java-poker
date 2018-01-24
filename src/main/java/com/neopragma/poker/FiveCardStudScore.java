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
    public Score score(Hand hand, Game game) {
        lookForStraightAndFlush(hand, game);
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

        if (countsBySet[0] == 4) {
            handValue = HandValue.FOUR_OF_A_KIND;
            highJunkCard = group.cardSets().get(group.cardSets().size() - 1).cards().get(0);
        } else if (countsBySet[0] == 3 && countsBySet[1] == 2) {
            handValue = HandValue.FULL_HOUSE;
            higherRank = group.cardSets().get(0).cards().get(0).rank();
            lowerRank = group.cardSets().get(1).cards().get(0).rank();
        }


        //TODO remove display
        System.out.println("in FiveCardStudScore.scoreGroup(), countsBySet: " + countsBySet[0] + ", " + countsBySet[1] + ", " + countsBySet[2]);

    }

    private void lookForStraightAndFlush(Hand hand, Game game) {
        handValue = HandValue.UNSCORED;
        boolean flush = flush(hand, game);
        boolean straight = straight(hand, game);
        higherRank = highCardRank(hand, game);

        if (flush && straight) {
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


    public Rank highCardRank(Hand hand, Game game) {
        return hand.show().get(0).rank();
    }
}
