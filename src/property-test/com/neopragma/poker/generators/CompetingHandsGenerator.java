package com.neopragma.poker.generators;

import com.neopragma.poker.*;
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
    public CompetingHandsGenerator() {
        super(CompetingHands.class);
        initialize();
    }

    private final static int FIRST_HAND = 0;
    private final static int SECOND_HAND = 1;
    private final static int FIRST_CARD = 0;
    private final static int SECOND_CARD = 1;
    private final static int THIRD_CARD = 2;
    private final static int FOURTH_CARD = 3;
    private final static int FIFTH_CARD = 4;
    MagicDeck deck;

    // Select HandValue values randomly, omitting UNSCORED (index 0)
    private HandValue[] handValues;
    private HandValue[] sourceValues;
    int handValuesArrayLength;
    private Random randomIndex = new Random();

    @Override
    public CompetingHands generate(SourceOfRandomness random, GenerationStatus status) {
        HandValue[] selectedHandValues = new HandValue[2];
        Hand[] hands = new Hand[2];
        for (int h = 0 ; h < 2 ; h++) {
            int handValueIndex = randomIndex.nextInt(handValuesArrayLength);
            selectedHandValues[h] = handValues[handValueIndex];
            Hand hand = null;
            //TODO add special methods for straights and flushes
            switch (selectedHandValues[h]) {
                case ROYAL_FLUSH:
                    hands[h] = makeHandWithRoyalFlush(random, status);
                    break;
                case STRAIGHT_FLUSH:
                    hands[h] = makeHandWithStraightFlush(random, status);
                    break;
                case FOUR_OF_A_KIND:
                    hands[h] = makeHandWith(random, status, HandValue.FOUR_OF_A_KIND, 4, 1);
                    break;
                case FULL_HOUSE:
                    hands[h] = makeHandWith(random, status, HandValue.FULL_HOUSE, 3, 2);
                    break;
                case FLUSH:
                    hands[h] = makeHandWithFlush(random, status);
                    break;
                case STRAIGHT:
                    hands[h] = makeHandWithStraight(random, status);
                    break;
                case TWO_PAIR:
                    hands[h] = makeHandWith(random, status, HandValue.TWO_PAIR, 2, 2, 1);
                    break;
                case THREE_OF_A_KIND:
                    hands[h] = makeHandWith(random, status, HandValue.THREE_OF_A_KIND, 3, 1, 1);
                    break;
                case PAIR:
                    hands[h] = makeHandWith(random, status, HandValue.PAIR, 2, 1, 1, 1);
                    break;
                default:
                    //TODO need a specific method to make junk hands that guarantees no pairs, flushes, straights.
                    hands[h] = makeHandWith(random, status, HandValue.JUNK, 1, 1, 1, 1, 1);
            }
        }

        //TODO need to set the correct Result for each pair of hands

        System.out.println("hand[0]: " + selectedHandValues[0] + " (" + selectedHandValues[0].ordinal() + ") , hand[1]: " + selectedHandValues[1] + " (" + selectedHandValues[1].ordinal() + ")");

        Result result = Result.TIE;
        if (selectedHandValues[0].ordinal() > selectedHandValues[1].ordinal()) {
            result = Result.WIN;
        } else if (selectedHandValues[0].ordinal() < selectedHandValues[1].ordinal()) {
            result = Result.LOSE;
        } else {
            result = tiebreaker(hands[0], hands[1], selectedHandValues[0]);
        }

        return new CompetingHands(hands[0], hands[1], result);
    }

    //TODO need logic here
    private Result tiebreaker(Hand hand1, Hand hand2, HandValue handValue) {
        int hand1_rankOrdinal;
        int hand2_rankOrdinal;
        int[] rankOrdinal;
        Result result = Result.TIE;
        switch (handValue) {
            case ROYAL_FLUSH:
                return Result.TIE; // we aren't handling suit ranking yet
            case FOUR_OF_A_KIND:
                result = compare(hand1, hand2, FIRST_CARD);     // high four
                if (result != Result.TIE) return result;
                return compare(hand1, hand2, FIFTH_CARD);      // junk card
            case STRAIGHT_FLUSH:
            case FLUSH:
            case STRAIGHT:
                result = compare(hand1, hand2, FIRST_CARD);     // compare hands high card to low card
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, SECOND_CARD);
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, THIRD_CARD);
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, FOURTH_CARD);
                if (result != Result.TIE) return result;
                return compare(hand1, hand2, FIFTH_CARD);
            case FULL_HOUSE:
                result = compare(hand1, hand2, FIRST_CARD);     // high three
                if (result != Result.TIE) return result;
                return compare(hand1, hand2, FOURTH_CARD);       // low pair
            case THREE_OF_A_KIND:
                result = compare(hand1, hand2, FIRST_CARD);     // pairs
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, FOURTH_CARD);     // high junk card
                if (result != Result.TIE) return result;
                return compare(hand1, hand2, FIFTH_CARD);       // low junk card
            case TWO_PAIR:
                result = compare(hand1, hand2, FIRST_CARD);     // pairs
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, THIRD_CARD);     // high junk card
                if (result != Result.TIE) return result;
                return compare(hand1, hand2, FIFTH_CARD);       // low junk card
            case PAIR:
                result = compare(hand1, hand2, FIRST_CARD);     // pairs
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, THIRD_CARD);     // high junk card
                if (result != Result.TIE) return result;
                result = compare(hand1, hand2, FOURTH_CARD);    // next junk card
                if (result != Result.TIE) return result;
                return compare(hand1, hand2, FIFTH_CARD);       // low junk card
            default:
                return Result.TIE;
        }
    }

    private Result compare(Hand hand1, Hand hand2, int index) {
        int rankOrdinal[] = getRankOrdinals(hand1, hand2, FIRST_CARD);
        if (rankOrdinal[FIRST_HAND] > rankOrdinal[SECOND_HAND]) {
            return Result.WIN;
        } else if (rankOrdinal[FIRST_HAND] < rankOrdinal[SECOND_HAND]) {
            return Result.LOSE;
        }
        return Result.TIE;
    }

    private int[] getRankOrdinals(Hand hand1, Hand hand2, int index) {
        return new int[]{
                hand1.show().get(index).rank().ordinal(),
                hand2.show().get(index).rank().ordinal(),
        };
    }

    /**
     * @param random
     * @param status
     * @return Hand containing a Royal Flush, using the suit of the first randomly-drawn card.
     */
    private Hand makeHandWithRoyalFlush(SourceOfRandomness random, GenerationStatus status) {
        deck = new MagicDeck();
        deck.shuffle();
        Suit suit = deck.remaining().get(0).suit();
        List<Card> cards = new ArrayList<>();
        cards.add(deck.draw(new PlayingCard(suit, Rank.ACE)));
        cards.add(deck.draw(new PlayingCard(suit, Rank.KING)));
        cards.add(deck.draw(new PlayingCard(suit, Rank.QUEEN)));
        cards.add(deck.draw(new PlayingCard(suit, Rank.JACK)));
        cards.add(deck.draw(new PlayingCard(suit, Rank.TEN)));
        return new Hand(cards);
    }

    private Hand makeHandWithStraightFlush(SourceOfRandomness random, GenerationStatus status) {
        deck = new MagicDeck();
        deck.shuffle();
        List<Rank> filterOutRanks = new ArrayList<>();
        filterOutRanks.add(Rank.ACE); // don't want royal flush
        Card card = deck.drawNextCard(filterOutRanks); // if this is above NINE we have to go down to make the straight
        Suit suit = card.suit(); // the suit for the flush
        int rankOrdinal = card.rank().ordinal(); // starting point to make the straight
        int increment = (rankOrdinal > 9) ? -1 : 1;  // if card higher than nine, we go downward to make the straight
        int cardCount = 0;
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        for (int i = rankOrdinal + increment ; cardCount < 4 ; i += increment) {
            cards.add(deck.draw(new PlayingCard(suit, Rank.values()[i])));
            cardCount++;
        }
        return new Hand(cards);
    }

    private Hand makeHandWithFlush(SourceOfRandomness random, GenerationStatus status) {
        Card card = deck.drawNextCard();
        Suit suit = card.suit();            // the suit for the flush
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        for (int i = 0  ; i < 4 ; i ++) {
            cards.add(deck.drawNextCardInSuit(suit));
        }
        return new Hand(cards);
    }

    private Hand makeHandWithStraight(SourceOfRandomness random, GenerationStatus status) {
        deck = new MagicDeck();
        deck.shuffle();
        Card card = deck.drawNextCard(new ArrayList<Rank>());
        int rankOrdinal = card.rank().ordinal(); // starting point to make the straight
        int increment = (rankOrdinal > 9) ? -1 : 1;  // if card higher than nine, we go downward to make the straight
        int cardCount = 0;
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        for (int i = rankOrdinal + increment ; i < cardCount ; i += increment) {
            cards.add(deck.drawNextCardOfRank(Rank.values()[i], card.suit()));
            cardCount++;
        }
        return new Hand(cards);
    }

    /**
     * @return Hand with the value specified.
     */
    private Hand makeHandWith(SourceOfRandomness random,
                              GenerationStatus status,
                              HandValue handValue,
                              int...counts) {
        deck = new MagicDeck();
        deck.shuffle();
        List<Card> cards = new ArrayList<>();
        List<Rank> filterOutRanks = new ArrayList<>();
        for (int i = 0 ; i < counts.length ; i++) {
            cards.add(deck.drawNextCard(filterOutRanks));
            filterOutRanks.add(cards.get(cards.size()-1).rank());
            cards = getMoreLikeThis(counts[i] - 1, cards, deck);
        }

        // Make sure the higher-ranking pair appears first in the hand
        // or the highest junk card in the hand appears first
        if (handValue == HandValue.TWO_PAIR) {
            List<Card> twoPair = new ArrayList<>();
            for (int i = 0 ; i < 4 ; i++) {
                twoPair.add(cards.get(i));
            }
            twoPair.sort((Card cards1, Card cards2) ->
                    cards2.rank().ordinal() - cards1.rank().ordinal()
            );
            for (int i = 0 ; i < 4 ; i++) {
                cards.set(i, twoPair.get(i));
            }
        } else if (handValue == HandValue.JUNK) {
                cards.sort((Card cards1, Card cards2) ->
                        cards2.rank().ordinal() - cards1.rank().ordinal()
                );
        }
        return new Hand(cards);
    }

    private List<Card> getMoreLikeThis(int howManyMore, List<Card> cards, MagicDeck deck) {
        Card card = cards.get(cards.size()-1);
        Suit suit = card.suit();
        Rank rank = card.rank();
        int suitOrdinal = suit.ordinal();
        for (int i = 0 ; i < howManyMore ; i++) {
            cards.add(new PlayingCard(Suit.values()[nextSuit(suitOrdinal)], rank));
            suitOrdinal = nextSuit(suitOrdinal);
        }
        return cards;
    }

    /**
     * Cycle through Suit ordinals 1-4. Don't use 0 (NONE).
     * @param suitOrdinal
     * @return next ordinal value between 1-4, circling around to 1 again
     * */
    private int nextSuit(int suitOrdinal) {
        int nextOrdinal = suitOrdinal + 1;
        if (suitOrdinal > 3) {
            nextOrdinal = 1;
        }
        return nextOrdinal;
    }

    private void initialize() {
        deck = new MagicDeck();
        handValuesArrayLength = HandValue.values().length - 1;
        handValues = new HandValue[handValuesArrayLength];
        sourceValues = HandValue.values();
        for (int targetIndex = 0 ; targetIndex < handValuesArrayLength ; targetIndex++) {
            int sourceIndex = targetIndex + 1;
            handValues[targetIndex] = sourceValues[sourceIndex];
        }
    }
}
