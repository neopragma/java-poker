package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

import java.util.List;

/**
 * A meaningful set of cards in a Hand.
 * @see Group
 * @author neopragma
 * @since 1.8
 */
class CardSetImpl extends AbstractCardSet {

    CardSetImpl(List<Card> cards) {
        Precondition.assertThat((null != cards && cards.size() > 0), new MessageId("M002"));
        this.cards = cards;
    }

}
