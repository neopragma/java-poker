package com.neopragma.poker;

import java.util.Map;

/**
 * Bag of data to pass back to Score implementations from the default findCardSets() method of Score.
 * @deprecated
 */
//TODO replace with Group
public class GroupResult {
    private Map<Rank, Integer> rankGroups;
    private Rank higherRank;
    private int higherCount;
    private Rank lowerRank;
    private int lowerCount;

    public GroupResult(Map<Rank, Integer> rankGroups,
               Rank higherRank,
               int higherCount,
               Rank lowerRank,
               int lowerCount) {
        this.rankGroups = rankGroups;
        this.higherRank = higherRank;
        this.higherCount = higherCount;
        this.lowerRank = lowerRank;
        this.lowerCount = lowerCount;
    }

    public Map<Rank, Integer> rankGroups() {
        return rankGroups;
    }

    public Rank higherRank() {
        return higherRank;
    }

    public int higherCount() {
        return higherCount;
    }

    public Rank lowerRank() {
        return lowerRank;
    }

    public int lowerCount() {
        return lowerCount;
    }

}
