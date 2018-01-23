package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

public class GroupTest {

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_constructor_is_called_with_null_HandValue() {
        new Group(null, new NullCardSet(), new NullCardSet(), new PlayingCard(Suit.DIAMONDS, Rank.KING));
    }

}
