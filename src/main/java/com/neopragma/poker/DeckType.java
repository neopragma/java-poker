package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

public enum DeckType {
    STANDARD("52 cards, Spades, Hearts, Diamonds, Clubs. No Jokers.");

    private String description;

    DeckType(String description) {
        Precondition.assertThat((null != description && description.length() > 0), new MessageId(("M005")));
        this.description = description;
    }

    public String description() {
        return description;
    }
}
