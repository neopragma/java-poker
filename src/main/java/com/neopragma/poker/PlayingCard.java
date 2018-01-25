package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

public class PlayingCard extends AbstractCard {

    public PlayingCard(Suit suit, Rank rank) {
        Precondition.assertThat(null != suit && null != rank, new MessageId("M003"));
        this.suit = suit;
        this.rank = rank;
    }
}
