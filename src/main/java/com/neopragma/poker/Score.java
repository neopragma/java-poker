package com.neopragma.poker;

import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * The basis for enums that calculate and represent the value of a hand based on the rules of various games.
 * @author neopragma
 */
public interface Score {

    /**
     * HandValue is available after calling score().
     * @return a HandValue enum.
     */
    HandValue handValue();

    /**
     * The rank that has the higher value when the hand contains more than one card set.
     * For example, in a Full House, eights over fours, the higherRank is Rank.EIGHT.
     * @return the Rank of the higher-value group in the hand.
     */
    Rank higherRank();

    /**
     * The rank that has the lower value when the hand contains more than one card set.
     * For example, in a Full House, eights over fours, the lowerRank is Rank.FOUR.
     * @return the Rank of the lower-value group in the hand.
     */
    Rank lowerRank();

    /**
     * The highest card in the hand that is not part of a matching rank group.
     * @return The highest card in the hand that is not part of a matching rank group.
     */
    Card highJunkCard();

    /**
     * The result of analyzing the hand according to the rules of the game.
     * @param hand The Hand to be evaluated.
     * @return an enum representing the hand's score
     */
    Score score(Hand hand);

    /**
     * Determine whether the Hand contains a flush. Has default implementation. Typically called from Score.score().
     * @param hand The Hand to be evaluated.
     * @return true if the Hand contains a flush.
     */
    default boolean flush(Hand hand) {
        boolean flush = true;
        List<Card> cards = hand.show();
        Suit suit = cards.get(0).suit();
        for (int i = 1 ; i < cards.size() ; i++) {
            if (suit != cards.get(i).suit()) {
                flush = false;
                break;
            }
        }
        return flush;
    }

    /**
     * Determine whether the Hand contains a straight. Has default implementation. Typically called from Score.score().
     * @param hand The Hand to be evaluated.
     * @return true if the Hand contains a straight.
     */
    default boolean straight(Hand hand) {
        boolean straight = true;
        List<Card> cards = hand.show();
        for (int i = 0 ; i < (cards.size()-1) ; i++) {
            if ( (cards.get(i).rank().ordinal() - cards.get(i + 1).rank().ordinal()) != 1 ) {
                straight = false;
                break;
            }
        }
        return straight;
    }

    /**
     * Determine whether the hand contains any matching ranks, such as three of a kind or pairs.
     * @param hand the Hand to be evaluated.
     * @return Group instance which describes pairs and so forth in the hand.
     */
    default Group findCardSets(Hand hand) {
        List<Card> cards = hand.show();

        // Group the cards of similar rank
        Map<Rank, List<Card>> rankGroups = new HashMap<>();
        for (Card card : cards) {
            if (!rankGroups.containsKey(card.rank())) {
                rankGroups.put(card.rank(), new ArrayList<>());
            }
            rankGroups.get(card.rank()).add(card);
        }

        // Put the grouped cards into card sets
        List<CardSet> cardSetsByRank = new ArrayList<>();
        for (Rank rank : rankGroups.keySet()) {
            cardSetsByRank.add(new CardSetImpl(rankGroups.get(rank)));
        }

        // Sort the card sets high to low by value
        List<CardSet> sortedCardSetsByRank = cardSetsByRank
                .stream()
                .sorted(Collections.reverseOrder())
                .collect(toList());

        return new Group(sortedCardSetsByRank);
    }

    /**
     * Determine a straight's "high card". Default implementation returns the highest rank in the hand.
     * @param hand The Hand to be evaluated.
     * @return Rank of the highest card in the hand.
     */
    default Rank highCardRank(Hand hand) {
        List<Card> cards = hand.show();
        cards.stream()
            .sorted()
            .collect(toList());
        return cards.get(0).rank();
    }

}

