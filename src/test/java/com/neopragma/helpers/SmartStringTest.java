package com.neopragma.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SmartStringTest {

    @Test
    public void it_accepts_a_value_consistent_with_default_parameters() {
        SmartString myString = new SmartString().withValue("This is a valid SmartString.");
        assertEquals("This is a valid SmartString.", myString.value());
    }

    @Test(expected = RuntimeException.class)
    public void it_throws_on_attempted_use_before_value_has_been_set() {
        SmartString myString = new SmartString();
        myString.value();
    }

    @Test(expected = RuntimeException.class)
    public void it_rejects_a_null_value_by_default() {
        SmartString myString = new SmartString().withValue(null);
    }

    @Test(expected = RuntimeException.class)
    public void it_rejects_an_empty_string_by_default() {
        SmartString myString = new SmartString().withValue("");
    }

    @Test(expected = RuntimeException.class)
    public void it_rejects_a_value_longer_than_the_specified_maximum_length() {
        SmartString myString = new SmartString().withMaximumLength(5).withValue("abcdef");
    }

    @Test
    public void it_accepts_a_null_value_when_nullable_is_specified() {
        SmartString myString = new SmartString().nullable().withValue(null);
        assertEquals(null, myString.value());
    }

    @Test
    public void it_accepts_an_empty_string_value_when_nullable_is_specified() {
        SmartString myString = new SmartString().nullable().withValue("");
        assertEquals("", myString.value());
    }

    @Test
    public void it_makes_a_deep_copy_of_a_valid_SmartString_object() {
        SmartString string1 = new SmartString().nullable().withMaximumLength(20).withValue("The rain in Spain");
        SmartString string2 = string1.copy();
        assertEquals(string1, string2);
    }

    @Test
    public void comparing_null_value_to_non_null_value_returns_less_than() {
        SmartString string1 = new SmartString().nullable().withValue(null);
        SmartString string2 = new SmartString().withValue("X");
        assertEquals(-1, string1.compareTo(string2));
    }

    @Test
    public void comparing_non_null_value_to_null_value_returns_greater_than() {
        SmartString string1 = new SmartString().withValue("X");
        SmartString string2 = new SmartString().nullable().withValue(null);
        assertEquals(1, string1.compareTo(string2));
    }

    @Test
    public void comparing_two_null_values_returns_equal() {
        SmartString string1 = new SmartString().nullable().withValue(null);
        SmartString string2 = new SmartString().nullable().withValue(null);
        assertEquals(0, string1.compareTo(string2));
    }

}
