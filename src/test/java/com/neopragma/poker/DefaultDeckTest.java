package com.neopragma.poker;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DefaultDeckTest {

    private Deck deck;

    @Before
    public void beforeEach() {
        deck = DeckFactory.newInstance();
    }

    @Test
    public void it_shuffles_the_deck() {
        List<Card> beforeShuffling = deck.remaining();
        deck.shuffle();
        List<Card> afterShuffling = deck.remaining();
        assertNotEquals(beforeShuffling, afterShuffling);
    }

    @Test
    public void it_deals_cards_to_players() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players = deck.deal(players, 2);
        assertEquals(2, players.get(0).hand().show().size());
    }
}
