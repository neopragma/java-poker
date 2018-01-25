package com.neopragma.poker;

import com.neopragma.poker.generators.Hands;
import com.neopragma.poker.generators.PlayingCards;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(JUnitQuickcheck.class)
public class HandScoringPropertyTest {
    @Property(trials=100)
    public void strongerHandWins(@From(Hands.class) Hand hand1, @From(Hands.class) Hand hand2, Result result) {
        for (Hand hand : new Hand[] { hand1, hand2 }) {
            for (Card card : hand.show()) {
                assumeThat(card.rank(), not(equalTo(Rank.JOKER)));
                assumeThat(card.rank(), not(equalTo(Rank.NONE)));
                assumeThat(card.suit(), not(equalTo(Suit.NONE)));
            }
        }
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