package com.neopragma.poker;

import java.util.ArrayList;
import java.util.List;

/**
 * In some games, the suits are ranked and a card's suit can be a tie-breaker for two otherwise-equivalent hands.
 * @author neopragma
 * @since 1.8
 */
public class SuitRanking {

    List<Suit> highToLow = new ArrayList<Suit>();

    /**
     * @param suits array contains the suits in high-to-low order; null is valid and means no suit ranking is in effect.
     */
    public SuitRanking(Suit...suits) {
        if (null != suits) {
            for (Suit suit : suits) {
                highToLow.add(suit);
            }
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
