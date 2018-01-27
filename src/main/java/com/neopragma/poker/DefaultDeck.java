package com.neopragma.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultDeck implements Deck {

    private DeckType type;
    private List<Card> contents;
    protected List<Card> remaining;

    protected DefaultDeck() {
        initializeContents();
    }

    /**
     * Distribute one card to each player until each player has 'count' cards.
     * @param players the players in the game
     * @param count number of cards per hand
     * @return list of players with updated hands
     */
    @Override
    public List<Player> deal(List<Player> players, int count) {
        for (Player player : players) {
            for (int i = 0 ; i < count ; i++) {
                player.take(remaining.remove(0), count);
            }
        }
        return players;
    }

    @Override
    public DeckType type() {
        return type;
    }

    @Override
    public List<Card> contents() {
        return contents;
    }

    @Override
    public List<Card> remaining() {
        return remaining;
    }

    @Override
    public void shuffle() {
        remaining = new ArrayList<>();
        remaining.addAll(contents);
        Collections.shuffle(remaining);
    }

    private void initializeContents() {
        contents = new ArrayList<>();

        contents.add(new PlayingCard(Suit.SPADES, Rank.ACE));
        contents.add(new PlayingCard(Suit.SPADES, Rank.KING));
        contents.add(new PlayingCard(Suit.SPADES, Rank.QUEEN));
        contents.add(new PlayingCard(Suit.SPADES, Rank.JACK));
        contents.add(new PlayingCard(Suit.SPADES, Rank.TEN));
        contents.add(new PlayingCard(Suit.SPADES, Rank.NINE));
        contents.add(new PlayingCard(Suit.SPADES, Rank.EIGHT));
        contents.add(new PlayingCard(Suit.SPADES, Rank.SEVEN));
        contents.add(new PlayingCard(Suit.SPADES, Rank.SIX));
        contents.add(new PlayingCard(Suit.SPADES, Rank.FIVE));
        contents.add(new PlayingCard(Suit.SPADES, Rank.FOUR));
        contents.add(new PlayingCard(Suit.SPADES, Rank.THREE));
        contents.add(new PlayingCard(Suit.SPADES, Rank.TWO));

        contents.add(new PlayingCard(Suit.HEARTS, Rank.ACE));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.KING));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.QUEEN));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.JACK));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.TEN));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.NINE));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.EIGHT));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.SEVEN));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.SIX));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.FIVE));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.FOUR));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.THREE));
        contents.add(new PlayingCard(Suit.HEARTS, Rank.TWO));

        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.ACE));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.KING));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.QUEEN));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.JACK));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.TEN));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.NINE));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.EIGHT));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.SEVEN));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.SIX));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.FIVE));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.FOUR));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.THREE));
        contents.add(new PlayingCard(Suit.DIAMONDS, Rank.TWO));

        contents.add(new PlayingCard(Suit.CLUBS, Rank.ACE));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.KING));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.QUEEN));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.JACK));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.TEN));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.NINE));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.EIGHT));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.SEVEN));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.SIX));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.FIVE));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.FOUR));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.THREE));
        contents.add(new PlayingCard(Suit.CLUBS, Rank.TWO));

        remaining = new ArrayList<>();
        remaining.addAll(contents);
    }

}
