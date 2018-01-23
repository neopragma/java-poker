package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

/**
 * Represents a set of cards in a Hand that have value, such as three of a kind or a pair.
 * @author neopragma
 * @since 1.8
 */
public class Group {

    private HandValue handValue;
    private CardSet highCardSet;
    private CardSet lowCardSet;
    private Card highJunkCard;

    public Group(HandValue handValue, CardSet highCardSet, CardSet lowCardSet, Card highJunkCard) {
        Precondition.assertThat(
                (handValue != null && highCardSet != null && lowCardSet != null && highJunkCard != null),
                new MessageId("M003"));
        this.handValue = handValue;
        this.highCardSet = highCardSet;
        this.lowCardSet = lowCardSet;
        this.highJunkCard = highJunkCard;
    }

}
