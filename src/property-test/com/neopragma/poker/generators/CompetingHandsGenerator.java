package com.neopragma.poker.generators;

import com.neopragma.helpers.MessageId;
import com.neopragma.poker.*;
import com.neopragma.preconditions.Precondition;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.util.*;

/**
 * Uses HandValue enum to select two hand values, then generates hands with those values
 * and sets the expected Result value accordingly. These data points are used to test the
 * applyScoringRules() method of Game via calls to Hand.beats(Hand).
 */
public class CompetingHandsGenerator extends Generator<CompetingHands> {

    //TODO two pair agsint two pair, same high pair, 2nd hand has winning low par - app gets it wrong. Test gets it right. Same for full house against full house. Same for single pair tied hands. Also two junk hands.

    public CompetingHandsGenerator() {
        super(CompetingHands.class);
        handValues = initializeHandValues();
        suits = initializeSuits();
        ranks = initializeRanks();
    }
    private HandValue[] handValues;
    private Suit[] suits;
    private Rank[] ranks;
    private Random randomIndex = new Random();

    // Values to be generated
    Hand[] hands;
    Result result;

    @Override
    public CompetingHands generate(SourceOfRandomness random, GenerationStatus status) {
        hands = new Hand[2];
        HandValue[] currentHandValues = new HandValue[2];
        result = Result.TIE;

        for (int h = 0 ; h < 2 ; h++) {
            int handValueIndex = randomIndex.nextInt(handValues.length);
            HandValue randomHandValue = handValues[handValueIndex];
            currentHandValues[h] = randomHandValue;

            switch (randomHandValue) {
                case ROYAL_FLUSH:
                    hands[h] = straight(randomSuit(),
                            Rank.ACE,
                            SuitMix.SAME);
                    break;
                case STRAIGHT_FLUSH:
                    hands[h] = straight(randomSuit(),
                            randomRankExcluding(Rank.ACE, Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO),
                            SuitMix.SAME);
                    break;
                case FOUR_OF_A_KIND:
                    hands[h] = fourOfAKind(randomRank());
                    break;
                case FULL_HOUSE:
                    hands[h] = fullHouse(randomRank());
                    break;
                case FLUSH:
                    hands[h] = flush(randomSuit());
                    break;
                case STRAIGHT:
                    hands[h] = straight(randomSuit(),
                            randomRankExcluding(Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO),
                            SuitMix.DIFFERENT);
                    break;
                case TWO_PAIR:
                    hands[h] = twoPair(randomRank());
                    break;
                case THREE_OF_A_KIND:
                    hands[h] = threeOfAKind(randomRank());
                    break;
                case PAIR:
                    hands[h] = pair(randomRank());
                    break;
                default:
                    hands[h] = junkHand();
            }
        }

        return new CompetingHands(hands[0], hands[1], expectedResult(hands, currentHandValues));
    }

    private Result expectedResult(Hand[] hands, HandValue[] currentHandValues) {
        Result result = Result.TIE;

        //TODO remove displays
        System.out.println("in expectedResult: values[0]: " + currentHandValues[0].ordinal() + ", values[1]: " + currentHandValues[1].ordinal());


        if (currentHandValues[0].ordinal() > currentHandValues[1].ordinal()) {
            return Result.WIN;
        }
        if (currentHandValues[0].ordinal() < currentHandValues[1].ordinal()) {
            return Result.LOSE;
        }
        HandValue tiedHandValue = currentHandValues[0];
        if (tiedHandValue == HandValue.STRAIGHT_FLUSH ||
                tiedHandValue == HandValue.STRAIGHT ||
                tiedHandValue == HandValue.FLUSH) {
            result = compareCards(hands, 0);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
        }
        if (tiedHandValue == HandValue.FOUR_OF_A_KIND) {
            result = compareCards(hands, 0);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
            result = compareCards(hands, 4);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
        }
        if (tiedHandValue == HandValue.FULL_HOUSE) {
            result = compareCards(hands, 0);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
            result = compareCards(hands, 3);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
        }
        if (tiedHandValue == HandValue.TWO_PAIR) {
            result = compareCards(hands, 0);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
            result = compareCards(hands, 2);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
            result = compareCards(hands, 4);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
        }
        if (tiedHandValue == HandValue.THREE_OF_A_KIND) {
            result = compareCards(hands, 0);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
            for (int i = 3 ; i < 5 ; i++) {
                result = compareCards(hands, 3);
                if (result == Result.WIN || result == Result.LOSE) {
                    return result;
                }
            }
        }
        if (tiedHandValue == HandValue.PAIR) {
            result = compareCards(hands, 0);
            if (result == Result.WIN || result == Result.LOSE) {
                return result;
            }
            for (int i = 2 ; i < 5 ; i++) {
                result = compareCards(hands, 3);
                if (result == Result.WIN || result == Result.LOSE) {
                    return result;
                }
            }
        }
        if (tiedHandValue == HandValue.JUNK) {
            for (int i = 0 ; i < 5 ; i++) {
                result = compareCards(hands, 3);
                if (result == Result.WIN || result == Result.LOSE) {
                    return result;
                }
            }
        }
        return Result.TIE;
    }

    private Result compareCards(Hand[] hands, int index) {
        int hand1_TopCard = hands[0].show().get(index).rank().ordinal();
        int hand2_TopCard = hands[1].show().get(index).rank().ordinal();

        //TODO remove displays
        System.out.println("in compareCards: hand1_Topcard: " + hand1_TopCard + ", hand2_TopCard: " + hand2_TopCard);


        if (hand1_TopCard > hand2_TopCard) {
            return Result.WIN;
        }
        if (hand1_TopCard < hand2_TopCard) {
            return Result.LOSE;
        }
        return Result.TIE;
    }


    /**
     * @return array of all possible HandValue values except NONE.
     */
    private HandValue[] initializeHandValues() {
        int len = HandValue.values().length ;
        HandValue[] handValues = new HandValue[len - 1];
        for (int v = 1 ; v < len ; v++) {
            handValues[v - 1] = HandValue.values()[v];
        }
        return handValues;
    }

    /**
     * @return array of all possible Suit values except NONE.
     */
    private Suit[] initializeSuits() {
        int len = Suit.values().length;
        Suit[] suits = new Suit[len - 1];
        for (int v = 1 ; v < len ; v++) {
            suits[v - 1] = Suit.values()[v];
        }
        return suits;
    }

    /**
     * @return array of all possible Rank values except NONE and JOKER.
     */
    private Rank[] initializeRanks() {
        int len = Rank.values().length;
        Rank[] ranks = new Rank[len - 2];
        for (int v = 2 ; v < len ; v++) {
            ranks[v - 2] = Rank.values()[v];
        }
        return ranks;
    }

    /**
     *
     * @param suit First suit to select
     * @param highCardRank Top ranked card in the straight, must be at least Six.
     * @param mix Mix.SAME to make all cards the same suit (flush), Mix.DIFFERENT for different suits.
     * @return Hand containing a straight, straight flush, or royal flush.
     */
    private Hand straight(Suit suit, Rank highCardRank, SuitMix mix) {
        Precondition.assertThat( (highCardRank.ordinal() > Rank.FIVE.ordinal()), new MessageId("M999") );
        Hand hand = new Hand();
        int start = 0;                                  // high card in the straight
        for (int i = 0 ; i < ranks.length ; i++) {
            if (highCardRank == ranks[i]) {
                start = i;
                break;
            }
        }
        int end = start - 5;                            // high card 5 or below not possible
        Suit nextSuit = suit;
        int rotateBy = (mix == SuitMix.SAME) ? 0 : 1;   // Mix.SAME rotate by 0, Mix.DIFFERENT rotate by 1
        for (int r = start ; r > end ; r--) {
            hand.add(new PlayingCard(nextSuit, ranks[r]));
            nextSuit = rotateSuits(nextSuit, rotateBy);
        }
        hand.show().sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );
        return hand;
    }

    private Hand fourOfAKind(Rank matchingRank) {
        Hand hand = new Hand();
        hand.add(new PlayingCard(Suit.SPADES, matchingRank));
        hand.add(new PlayingCard(Suit.HEARTS, matchingRank));
        hand.add(new PlayingCard(Suit.DIAMONDS, matchingRank));
        hand.add(new PlayingCard(Suit.CLUBS, matchingRank));
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(matchingRank)));
        return hand;
    }

    private Hand fullHouse(Rank highRank) {
        Hand hand = new Hand();
        hand.add(new PlayingCard(Suit.SPADES, highRank));
        hand.add(new PlayingCard(Suit.HEARTS, highRank));
        hand.add(new PlayingCard(Suit.DIAMONDS, highRank));
        Rank lowRank = randomRankExcluding(highRank);
        hand.add(new PlayingCard(Suit.CLUBS, lowRank));
        hand.add(new PlayingCard(randomSuit(), lowRank));
        return hand;
    }

    private Hand flush(Suit matchingSuit) {
        Hand hand = new Hand();
        List<Rank> excludedRanks = new ArrayList<>();
        excludedRanks.add(Rank.ACE);
        hand.add(new PlayingCard(matchingSuit,
                randomRankExcluding(excludedRanks.toArray(new Rank[excludedRanks.size()]))));
        for (int i = 0 ; i < 5 ; i++) {
            Rank thisRank = hand.show().get(hand.show().size() - 1).rank();
            excludedRanks.add(thisRank);
            hand.add(new PlayingCard(matchingSuit,
                    randomRankExcluding(excludedRanks.toArray(new Rank[excludedRanks.size()]))));
        }
        hand.show().sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );
        return hand;
    }

    private Hand twoPair(Rank highRank) {
        Hand hand = new Hand();
        hand.add(new PlayingCard(Suit.SPADES, highRank));
        hand.add(new PlayingCard(Suit.HEARTS, highRank));
        Rank lowRank = randomRankExcluding(highRank);
        hand.add(new PlayingCard(Suit.CLUBS, lowRank));
        hand.add(new PlayingCard(randomSuit(), lowRank));
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(highRank, lowRank)));

        List<Card> cards = hand.show();
        int firstPairRank = cards.get(0).rank().ordinal();
        int secondPairRank = cards.get(2).rank().ordinal();
        if (firstPairRank < secondPairRank) {
            swap(cards, new int[] {0, 2}, new int[] {2, 4});
        }
        return hand;
    }

    private Hand threeOfAKind(Rank matchingRank) {
        Hand hand = new Hand();
        hand.add(new PlayingCard(Suit.SPADES, matchingRank));
        hand.add(new PlayingCard(Suit.HEARTS, matchingRank));
        hand.add(new PlayingCard(Suit.DIAMONDS, matchingRank));
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(matchingRank)));
        Rank lastRank = hand.show().get(hand.show().size()-1).rank();
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(matchingRank, lastRank)));

        // Sort the bottom two junk cards in case of a tiebreak
        List<Card> bottomTwo = new ArrayList<>();
        for (int i = 3 ; i < 5 ; i++) bottomTwo.add(hand.show().get(i));
        bottomTwo.sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );
        for (int i = 0 ; i < 2 ; i++) hand.show().set(i+3, bottomTwo.get(i));

        return hand;
    }

    private Hand pair(Rank matchingRank) {
        Hand hand = new Hand();
        hand.add(new PlayingCard(Suit.SPADES, matchingRank));
        hand.add(new PlayingCard(Suit.HEARTS, matchingRank));
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(matchingRank)));
        Rank lastRank = hand.show().get(hand.show().size()-1).rank();
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(matchingRank, lastRank)));
        Rank lowRank = hand.show().get(hand.show().size()-1).rank();
        hand.add(new PlayingCard(randomSuit(), randomRankExcluding(matchingRank, lastRank, lowRank)));

        // Sort the bottom three junk cards in case of a tiebreak
        List<Card> bottomThree = new ArrayList<>();
        for (int i = 2 ; i < 5 ; i++) bottomThree.add(hand.show().get(i));
        bottomThree.sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );
        for (int i = 0 ; i < 2 ; i++) hand.show().set(i+2, bottomThree.get(i));

        return hand;
    }

    private Hand junkHand() {
        Hand hand = new Hand();
        Rank firstRank = randomRank();
        hand.add(new PlayingCard(Suit.CLUBS, firstRank));
        List<Rank> excludedRanks = new ArrayList<>();

        excludedRanks.add(firstRank);
        excludedRanks.addAll(neighboringRanksOf(firstRank));
        hand.add(new PlayingCard(Suit.DIAMONDS,
                randomRankExcluding(excludedRanks.toArray(new Rank[excludedRanks.size()]))));

        for (int i = 0 ; i < 3; i++){
            excludedRanks.add(hand.show().get(hand.show().size() - 1).rank());
            hand.add(new PlayingCard(randomSuit(),
                    randomRankExcluding(excludedRanks.toArray(new Rank[excludedRanks.size()]))));
        }

        hand.show().sort((Card cards1, Card cards2) ->
                cards2.rank().ordinal() - cards1.rank().ordinal()
        );

        return hand;
    }

    private void swap(List<Card> cards, int[] set1, int[] set2) {
        List<Card> temp = new ArrayList<>();
        for (int i1 = set1[0] ; i1 < set1[1]; i1++) temp.add(cards.get(i1));
        for (int i1 = set1[0], i2 = set2[0]; i1 < set1[1]; i1++, i2++) cards.set(i1, cards.get(i2));
        for (int i2 = set2[0], t = 0; i2 < set2[1]; i2++, t++) cards.set(i2, temp.get(t));
    }

    private Suit rotateSuits(Suit suit, int rotateBy) {
        if (rotateBy == 0) {
            return suit;
        }
        int newIndex = 0;
        for (int i = 0 ; i < suits.length ; i++) {
            if (suit == suits[i]) {
                newIndex = i;
                break;
            }
        }
        if (rotateBy > 0) {
            if (newIndex == 3) {
                newIndex = 0;
            } else {
                newIndex += rotateBy;
            }
        } else {
            if (newIndex == 0) {
                newIndex = 3;
            } else {
                newIndex += rotateBy;
            }
        }
        return suits[newIndex];
    }

    private Suit randomSuit() {
        int suitIndex = randomIndex.nextInt(suits.length);
        return suits[suitIndex];
    }

    private Rank randomRank() {
        return ranks[randomIndex.nextInt(ranks.length)];
    }

    private Rank randomRankExcluding(Rank...excludedRanks) {
        Rank randomRank = null;
        boolean available = false;
        do {
            available = true;
            randomRank = ranks[randomIndex.nextInt(ranks.length)];
            for (Rank rankToExclude : excludedRanks) {
                if (randomRank == rankToExclude) {
                    available = false;
                }
            }
        } while (!available);
        return randomRank;
    }

    private List<Rank> neighboringRanksOf(Rank thisRank) {
        List<Rank> neighboringRanks = new ArrayList<>();
        int thisRankIndex = 0;
        for (int i = 0 ; i < ranks.length ; i++) {
            if (thisRank == ranks[i]) {
                thisRankIndex = i;
                break;
            }
        }
        if (thisRankIndex < ranks.length - 1) {
            neighboringRanks.add(ranks[thisRankIndex + 1]);
        }
        if (thisRankIndex > 0) {
            neighboringRanks.add(ranks[thisRankIndex - 1]);
        }
        return neighboringRanks;
    }


}
