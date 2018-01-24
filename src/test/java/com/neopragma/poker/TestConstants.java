package com.neopragma.poker;

public interface TestConstants {

    String FIVE_CARD_STUD = "Five AbstractCard Stud";

    int LOWER = -1;
    int SAME = 0;
    int HIGHER = 1;
    String EXPECT_LOWER = "Expected result to be -1 (LOWER)";
    String EXPECT_SAME = "Expected result to be 0 (SAME)";
    String EXPECT_HIGHER = "Expected result to be 1 (HIGHER)";
    SuitRanking standardSuitRanking =
            new SuitRanking(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS);
}
