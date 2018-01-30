package com.neopragma.poker;

import com.neopragma.helpers.MessageId;
import com.neopragma.preconditions.Precondition;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataDrivenTest {

    private String filename = "test-data.csv";
    private String line;

    @Test
    public void dataDrivenTest() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        boolean header = true;
        while((line = reader.readLine()) != null) {
            if (header) {
                header = false;
                continue;
            }
            String[] params = line.split(",");
            strongerHandWins(params[1],
                    makeHand(params[2], params[3], params[4], params[5], params[6]),
                    makeHand(params[7], params[8], params[9], params[10], params[11]),
                    Result.valueOf(params[12]));
        }
        reader.close();
    }


    private void strongerHandWins(String message, Hand hand1, Hand hand2, Result expectedResult) {
        Game game = new FiveCardStudGame();
        assertEquals(message(message, hand1, hand2), expectedResult, hand1.beats(hand2, game));
    }

    private String message(String description, Hand...hands) {
        StringBuilder message = new StringBuilder();
        message.append(description);
        for (int i = 0 ; i < hands.length ; i++) {
            message.append("\nHand " + (i+1) + ":\n");
            for (Card card : hands[i].show()) {
                message.append("\t" + card.rank() + " of " + card.suit() + "\n");
            }
        }
        return message.toString();
    }

    private Hand makeHand(String...cardAbbrev) {
        Precondition.assertThat((cardAbbrev.length == 5), new MessageId("M998"));
        List<Card> cards = new ArrayList<>();
        for (String abbrev : cardAbbrev) {
            cards.add(new PlayingCard(suit(abbrev), rank(abbrev)));
        }
        return new Hand(cards);
    }

    private Suit suit(String abbrev) {
        char suitCode = abbrev.charAt(abbrev.length()-1);
        switch (suitCode) {
            case 'S' : return Suit.SPADES;
            case 'H' : return Suit.HEARTS;
            case 'D' : return Suit.DIAMONDS;
            case 'C' : return Suit.CLUBS;
            default : return Suit.NONE;
        }
    }

    private Rank rank(String abbrev) {
        char rankCode = abbrev.charAt(0);
        switch (rankCode) {
            case 'A' : return Rank.ACE;
            case 'K' : return Rank.KING;
            case 'Q' : return Rank.QUEEN;
            case 'J' : return Rank.JACK;
            case '1' : return Rank.TEN;
            case '2' : return Rank.TWO;
            case '3' : return Rank.THREE;
            case '4' : return Rank.FOUR;
            case '5' : return Rank.FIVE;
            case '6' : return Rank.SIX;
            case '7' : return Rank.SEVEN;
            case '8' : return Rank.EIGHT;
            case '9' : return Rank.NINE;
            default : return Rank.NONE;
        }
    }

}
