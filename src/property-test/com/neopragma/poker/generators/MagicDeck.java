package com.neopragma.poker.generators;

import com.neopragma.poker.*;

import java.util.List;

public class MagicDeck extends StandardDeck {

    public MagicDeck() {}

    /**
     * Draw a particular card
     * @param cardToDraw
     * @return the specified card from the deck
     */
    public Card draw(Card cardToDraw) {
        int cardIx = remaining.indexOf(cardToDraw);
        Card card = remaining.get(cardIx);
        return remaining.remove(cardIx);
    }

    /**
     * Draw the next card, whatever it may be
     * @return next card in the deck
     */
    public Card drawNextCard() {
        return remaining.get(0);
    }

    /**
     * Draw the next card that is not one of the specified Ranks
     * @param filterOutRanks the Rank values we don't want
     * @return the next card that is not one of the specified Ranks
     */
    public Card drawNextCard(List<Rank> filterOutRanks) {
        int indexToDraw = 0;
        for (int i = 0 ; i < filterOutRanks.size() ; i++) {
            for ( ; indexToDraw < remaining.size() ; indexToDraw++) {
                if (remaining.get(indexToDraw).rank() != filterOutRanks.get(i)) {
                    break;
                }
            }
        }
        return remaining.remove(indexToDraw);
    }

    /**
     * Draw the next card of the desired Rank, excluding the specified Suit.
     * This is for making straights.
     * @param desiredRank
     * @return card of the specified Rank that is not in the excluded Suit.
     */
    public Card drawNextCardOfRank(Rank desiredRank, Suit excludedSuit) {
        int indexToDraw = 0;
        for (indexToDraw = 0 ; indexToDraw < remaining.size() ; indexToDraw++) {
            Card card = remaining.get(indexToDraw);
            if (card.rank() == desiredRank && card.suit() != excludedSuit) {
                break;
            }
        }
        return remaining.remove(indexToDraw);
    }

    /**
     * Draw the next card of the desired suit. This is for making flushes.
     * @param desiredSuit
     * @return next card from the deck of the desired Suit
     */
    public Card drawNextCardInSuit(Suit desiredSuit) {
        int indexToDraw = 0;
        Card card = new NullCard();
        for (indexToDraw = 0 ; indexToDraw < remaining.size() ; indexToDraw++) {
            if (remaining.get(indexToDraw).suit() == desiredSuit) {
                break;
            }
        }
        return remaining.remove(indexToDraw);
    }
}
