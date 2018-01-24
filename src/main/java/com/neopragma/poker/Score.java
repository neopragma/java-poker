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

    //TODO replace with Group
    /**
     * The rank that has the higher value when the hand contains more than one group.
     * For example, in a Full House, eights over fours, the higherRank is Rank.EIGHT.
     * @return the Rank of the higher-value group in the hand.
     */
    Rank higherRank();
    int higherCount();

    //TODO replace with Group
    /**
     * The rank that has the lower value when the hand contains more than one group.
     * For example, in a Full House, eights over fours, the lowerRank is Rank.FOUR.
     * @return the Rank of the lower-value group in the hand.
     */
    Rank lowerRank();
    int lowerCount();

    /**
     * The result of analyzing the hand according to the rules of the game.
     * @param hand The Hand to be evaluated.
     * @param game Any special rules to be applied (such as wild cards).
     * @return an enum representing the hand's score
     */
    Score score(Hand hand, Game game);

    /**
     * Determine whether the Hand contains a flush. Has default implementation. Typically called from Score.score().
     * @param hand The Hand to be evaluated.
     * @param game Any special rules to be applied (such as wild cards).
     * @return true if the Hand contains a flush.
     */
    default boolean flush (Hand hand, Game game) {
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
     * @param game Any special rules to be applied (such as wild cards).
     * @return true if the Hand contains a straight.
     */
    default boolean straight(Hand hand, Game game) {
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
     * @param game Any special rules to be applied (such as wild cards).
     * @return List of Group instances, which describe pairs and so forth.
     */
    //default GroupResult groups(Hand hand, Game game) {
    default void groups(Hand hand, Game game) {
        //TODO change return type to Group[] or List<Group> instead of GroupResult
        List<Card> cards = hand.show();

        // Group the cards of similar rank
        //TODO change to <Rank, CardSet> after adding Comparable support to CardSet
        Map<Rank, List<Card>> rankGroups = new HashMap<Rank, List<Card>>();
        for (Card card : cards) {
            if (false == rankGroups.containsKey(card.rank())) {
                rankGroups.put(card.rank(), new ArrayList<Card>());
            }
            rankGroups.get(card.rank()).add(card);
        }

        Map<Map<Integer, Rank>, List<Card>> cardsByCountAndRank = new HashMap<>();
        for (Rank rank : rankGroups.keySet()) {
            Collections.reverse(rankGroups.get(rank));
            Map<Integer, Rank> ranksByCount = new HashMap<Integer, Rank>();
            ranksByCount.put(rankGroups.get(rank).size(), rank);
            cardsByCountAndRank.put(ranksByCount, rankGroups.get(rank));
        }

        System.out.println("What's in cardsByCountAndRank?");
        System.out.println("cardsByCountAndRank: " + cardsByCountAndRank);



//        Map<Integer, List<Card>> rankGroupsByCount = new HashMap<>();
//        for (Rank rank : rankGroups.keySet()) {
//            Collections.reverse(rankGroups.get(rank));
//            rankGroupsByCount.put(rankGroups.get(rank).size(), rankGroups.get(rank));
//        }

        Map sortedRankGroups = cardsByCountAndRank
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        for (Rank rank : rankGroups.keySet()) {
            //TODO remove display
            System.out.println("In Score.groups()");
            System.out.println("rank: " + rank + ", count: " + rankGroups.get(rank).size());
        }

//        Rank higherRank = null;
//        int higherCount = 0;
//        Rank lowerRank = null;
//        int lowerCount = 0;
//        Rank tempRank = null;
//        for (Rank rank : rankGroups.keySet()) {
//            int count = rankGroups.get(rank).size();
//            if (count > higherCount) {
//                lowerRank = higherRank;
//                lowerCount = higherCount;
//                higherRank = rank;
//                higherCount = count;
//            } else if (count > lowerCount) {
//                lowerRank = rank;
//                lowerCount = count;
//            }
//        }
//        if (higherCount == lowerCount) {
//            if (lowerRank.ordinal() > higherRank.ordinal()) {
//                tempRank = higherRank;
//                higherRank = lowerRank;
//                lowerRank = tempRank;
//            }
//        }

        //TODO remove displays
//        System.out.println("higherRank: " + higherRank + ", " + higherCount);
//        System.out.println("lowerRank: " + lowerRank + ", " + lowerCount);


        CardSet highCardSet = null;
        CardSet lowCardSet = null;
        Card highJunkCard = null;

        //TODO remove displays

        System.out.println("Rank.THREE.compareTo(Rank.SEVEN) ? " + (Rank.THREE.compareTo(Rank.SEVEN)));
        System.out.println("sortedRankGroups contains these types:");
        System.out.println("key is type " + sortedRankGroups.keySet().toArray()[0].getClass().getName());
        System.out.println("number of entries is " + sortedRankGroups.size());
        System.out.println("value is type " + sortedRankGroups.get(sortedRankGroups.keySet().toArray()[0]).getClass().getName());

        System.out.println("sortedRankGroups");
        for (Object o : sortedRankGroups.keySet()) {
            System.out.println("for integer " + o + " we have " + sortedRankGroups.get(o));
        }
        //TODO change this to return Group[] or List<Group>
        //return new GroupResult(rankGroups, higherRank, higherCount, lowerRank, lowerCount);
    }


    /**
     * Determine the hand's "high card". Default implementation returns the highest rank in the hand. You have to override this method in a Score enum depending on the rules of the game (high-low, wild cards, etc.).
     * @param hand The Hand to be evaluated.
     * @param game Any special rules to be applied (such as wild cards).
     * @param startingFrom The index into the sorted cards in the hand where the highest-ranking card is located. Default is 0. If the score() method determined the hand contains a pair, then it would pass 2 as this param value to pick up the rank of the highest card after the pair (0, 1, 2).
     * @return Rank of the highest card in the hand after the value cards (e.g., those that make up a pair).
     */
    default Rank highCardRank(Hand hand, Game game, int startingFrom) {
        return hand.show().get(startingFrom()).rank();
    }

    default int startingFrom() {
        return 0;
    }

}

