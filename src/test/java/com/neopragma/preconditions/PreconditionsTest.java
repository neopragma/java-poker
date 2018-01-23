package com.neopragma.preconditions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PreconditionsTest {

    @Test
    public void it_throws_and_uses_resource_bundle() {
        try {
            Precondition.assertThat(false, "M001", "hey");
            fail("Expected PreconditionNotMetException but nothing was thrown.");
        } catch (PreconditionNotMetException expectedException) {
            assertEquals(
                    "In com.neopragma.preconditions.PreconditionsTest.it_throws_and_uses_resource_bundle: Argument <hey> cannot be null",
                    expectedException.getMessage());
        } catch (Exception unexpectedException) {
            fail("Expected PreconditionNotMetException but " + unexpectedException + " was thrown.");
        }
    }

    @Test
    public void it_throws_and_uses_provided_message() {
        try {
            Precondition.assertThat(false, "message");
            fail("Expected PreconditionNotMetException but nothing was thrown.");
        } catch (PreconditionNotMetException expectedException) {
            assertEquals(
                    "In com.neopragma.preconditions.PreconditionsTest.it_throws_and_uses_provided_message: message",
                    expectedException.getMessage());
        } catch (Exception unexpectedException) {
            fail("Expected PreconditionNotMetException but " + unexpectedException + " was thrown.");
        }
    }

    @Test
    public void it_does_not_throw_when_precondition_is_met() {
        try {
            Precondition.assertThat(true, "message");
        } catch (Exception unexpectedException) {
            fail("Did not expect an exception to be thrown here.");
        }
    }
}
