package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

import java.util.List;

/**
 * Represents sets of cards in a Hand that have value, such as threes and pairs. The high junk card is last in the list as a set of 1, if applicable.
 * @author neopragma
 * @since 1.8
 */
class Group {

    private List<CardSet> cardSets;

    /**
     * All the rank-grouped cards in a hand that add to the hand's value
     * @param cardSets assumed to be sorted descending by value (three-of-a-kind before pairs, junk card last)
     */
    public Group(List<CardSet> cardSets) {
        Precondition.assertThat(
                (cardSets != null && !cardSets.isEmpty()),
                new MessageId("M004"));
        this.cardSets = cardSets;
    }

    public List<CardSet> cardSets() {
        return cardSets;
    }

}
