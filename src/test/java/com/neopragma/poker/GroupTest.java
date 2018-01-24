package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GroupTest {

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_the_list_of_card_sets_is_null() {
        new Group(null);
    }

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_the_list_of_card_sets_is_empty() {
        new Group(new ArrayList<CardSet>());
    }

    @Test
    public void it_returns_card_sets_in_the_same_order_as_they_were_defined() {
        List<CardSet> cardSetList = new ArrayList<>();
        cardSetList.add(
                new CardSetImpl(new ArrayList<Card>(Arrays.asList(
                        new PlayingCard(Suit.DIAMONDS, Rank.QUEEN),
                        new PlayingCard(Suit.SPADES, Rank.QUEEN))
                )));
        cardSetList.add(
                new CardSetImpl(new ArrayList<Card>(Arrays.asList(
                        new PlayingCard(Suit.DIAMONDS, Rank.THREE),
                        new PlayingCard(Suit.SPADES, Rank.THREE))
                )));
        cardSetList.add(
                new CardSetImpl(new ArrayList<Card>(Arrays.asList(
                        new PlayingCard(Suit.CLUBS, Rank.ACE))
                )));
        Group group = new Group(cardSetList);
        assertCardSet_n_Contains(group, 0, 2, Rank.QUEEN);
        assertCardSet_n_Contains(group, 1, 2, Rank.THREE);
        assertCardSet_n_Contains(group, 2, 1, Rank.ACE);
    }

    private void assertCardSet_n_Contains(Group group, int index, int count, Rank rank) {
        CardSet cardSet = group.cardSets().get(index);
        String message = "Expected CardSet index {0} to contain {1} cards of rank {2}" +
                "\nbut it contained {3} cards of rank {4}";
        int cardSetSize = cardSet.cards().size();
        Rank cardRank = cardSet.cards().get(0).rank();
        String formattedMessage = MessageFormat.format(message, index, count, rank, cardSetSize, cardRank);

        assertEquals(formattedMessage,
            true,
                (cardSet.cards().size() == count && cardSet.cards().get(0).rank() == rank));
    }

}
