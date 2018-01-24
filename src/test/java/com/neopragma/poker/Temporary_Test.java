package com.neopragma.poker;

import org.junit.Test;

import javax.lang.model.util.SimpleElementVisitor6;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Temporary_Test {

    Hand hand = new Hand(new PlayingCard[]{
                    new PlayingCard(Suit.SPADES, Rank.KING),
                    new PlayingCard(Suit.SPADES, Rank.THREE),
                    new PlayingCard(Suit.DIAMONDS, Rank.THREE),
                    new PlayingCard(Suit.CLUBS, Rank.SEVEN),
                    new PlayingCard(Suit.DIAMONDS, Rank.SEVEN)
            }
    );

    @Test
    public void temp() {
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

        //TODO remove displays
        System.out.println("Want to see CardSet objects sorted descending by Rank");
        for (CardSet cardSet : sortedCardSetsByRank) {
            System.out.println("Next CardSet");
            for (Card card : cardSet.cards()) {
                System.out.println("Card: " + card.rank() + " of " + card.suit());
            }
        }


    }
}
