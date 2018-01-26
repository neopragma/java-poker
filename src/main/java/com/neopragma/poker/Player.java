package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;

/** Represents the domain concept of a 'player'
 * An entity, typically a human, who is playing poker.
 * @author neopragma
 * @since 1.8
 */
public class Player {

    private String name;
    private Hand hand = new Hand();

    public Player(String name) {
        Precondition.assertThat((null != name && name.length() > 0), new MessageId("M006"));
        this.name = name;
    }

    public Hand hand() {
        return hand;
    }

    public boolean take(Card card, int limit) {
        if (hand.show().size() >= limit) {
            return false;
        }
        hand.add(card);
        return true;
    }
}
