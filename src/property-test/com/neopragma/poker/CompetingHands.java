package com.neopragma.poker;

/**
 * This class is used for generated property-based test data.
 * @see {com.neopragma.poker.generators.CompetingHandsGenerator}
 * @author neopragma
 * @since 1.8
 */
public class CompetingHands {

    private Hand hand1;
    private Hand hand2;
    private Result result;

    public CompetingHands(Hand hand1, Hand hand2, Result result) {
        this.hand1 = hand1;
        this.hand2 = hand2;
        this.result = result;
    }

    public Hand hand1() {
        return hand1;
    }

    public Hand hand2() {
        return hand2;
    }

    public Result result() {
        return result;
    }
}
