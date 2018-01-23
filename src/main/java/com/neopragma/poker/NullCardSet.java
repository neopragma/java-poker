package com.neopragma.poker;

import java.util.ArrayList;
import java.util.List;

public class NullCardSet implements CardSet {

    private List<Card> cards = new ArrayList<>();

    @Override
    public List<Card> cards() {
        return cards;
    }
}
