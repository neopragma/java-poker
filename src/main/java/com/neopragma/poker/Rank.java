package com.neopragma.poker;

public enum Rank {

    NONE(),
    JOKER(),
    TWO(),
    THREE(),
    FOUR(),
    FIVE(),
    SIX(),
    SEVEN(),
    EIGHT(),
    NINE(),
    TEN(),
    JACK(true),
    QUEEN(true),
    KING(true),
    ACE(false, true);

    /** AbstractCard is considered a face card */
    private boolean faceCard;
    /** AbstractCard may be treated as high or low */
    private boolean highLow;

    Rank() {
        this(false, false);
    }

    Rank(boolean faceCard) {
        this(faceCard, false);
    }

    Rank(boolean faceCard, boolean highLow) {
        this.faceCard = faceCard;
        this.highLow = highLow;
    }

    public boolean isFaceCard() {
        return faceCard;
    }

    public boolean isHighLow() {
        return highLow;
    }

    public int value() {
        return ordinal();
    }

}
