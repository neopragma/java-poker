package com.neopragma.poker;

import com.neopragma.poker.generators.CompetingHandsGenerator;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(JUnitQuickcheck.class)
public class HandScoringPropertyTest {

    @Property(trials=100)
    public void strongerHandWins(@From(CompetingHandsGenerator.class) CompetingHands data) {
        Hand hand1 = data.hand1();
        Hand hand2 = data.hand2();
        Result result = data.result();
        Game game = new FiveCardStudGame();
        assertEquals(showHands(hand1, hand2), result, hand1.beats(hand2, game));
    }

    private String showHands(Hand...hands) {
        StringBuilder message = new StringBuilder();
        for (int i = 0 ; i < hands.length ; i++) {
            message.append("\nHand " + (i+1) + ":\n");
            for (Card card : hands[i].show()) {
                message.append("\t" + card.rank() + " of " + card.suit() + "\n");
            }
        }
        return message.toString();
    }
}