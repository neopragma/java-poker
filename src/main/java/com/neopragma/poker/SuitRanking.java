package com.neopragma.poker;

import java.util.ArrayList;
import java.util.List;

public class SuitRanking {

    List<Suit> highToLow = new ArrayList<Suit>();

    public SuitRanking(Suit...suits) {
        for (Suit suit : suits) {
            highToLow.add(suit);
        }
    }

    public Result suitBeatsSuit(Suit suit1, Suit suit2) {
        return highToLow.isEmpty() ? Result.TIE :
               highToLow.indexOf(suit1) < highToLow.indexOf(suit2) ? Result.WIN :
               Result.LOSE;
    }

    public boolean suitsAreRanked() {
        return !highToLow.isEmpty();
    }

}
