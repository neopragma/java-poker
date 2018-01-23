package com.neopragma.poker;

import com.neopragma.preconditions.PreconditionNotMetException;
import org.junit.Test;

public class GroupTest {

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_constructor_is_called_with_null_HandValue() {
        new Group(null, new NullCardSet(), new NullCardSet(), new NullCard());
    }

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_constructor_is_called_with_null_high_CardSet() {
        new Group(HandValue.UNSCORED, null, new NullCardSet(), new NullCard());
    }

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_constructor_is_called_with_null_low_CardSet() {
        new Group(HandValue.UNSCORED, new NullCardSet(), null, new NullCard());
    }

    @Test(expected=PreconditionNotMetException.class)
    public void it_throws_when_constructor_is_called_with_null_high_junk_card() {
        new Group(HandValue.UNSCORED, new NullCardSet(), new NullCardSet(), null);
    }

}
