package com.neopragma.poker;

/**
 * Instantiate different types of Deck objects for various poker variants.
 * @author neopragma
 * @since 1.8
 */
public class DeckFactory {

    static Deck newInstance() {
        return newInstance(DeckType.STANDARD);
    }

    static Deck newInstance(DeckType type) {
        switch (type) {
            case STANDARD : return new StandardDeck();
            default       : return new StandardDeck();
        }
    }
}
