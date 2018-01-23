package com.neopragma.poker;

public enum HandValue {
    UNSCORED(0, "Unscored - invalid"),
    JUNK(0, "High card"),
    PAIR(2, "Pair"),
    TWO_PAIR(4, "Two pair"),
    THREE_OF_A_KIND(6, "Three of a kind"),
    STRAIGHT(8, "Straight"),
    FLUSH(10, "Flush"),
    FULL_HOUSE(12, "Full house"),
    FOUR_OF_A_KIND(14, "Four of a kind"),
    STRAIGHT_FLUSH(16, "Straight flush"),
    ROYAL_FLUSH(18, "Royal flush");

    private int numericalValue;
    private String displayName;

    HandValue(int numericalValue, String displayName) {
        this.numericalValue = numericalValue;
        this.displayName = displayName;
    }

    public int numericalValue() {
        return numericalValue;
    }

    public String displayName() {
        return displayName;
    }

}
