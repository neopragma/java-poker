package com.neopragma.poker.generators;

import com.neopragma.poker.Hand;
import com.neopragma.poker.PlayingCard;
import com.neopragma.poker.Rank;
import com.neopragma.poker.Suit;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class Hands extends Generator<Hand> {
    public Hands() {
        super(Hand.class);
    }
    @Override
    public Hand generate(SourceOfRandomness random, GenerationStatus status) {
        PlayingCard[] playingCards = new PlayingCard[5];
        for (int i = 0 ; i < playingCards.length ; i++) {
            playingCards[i] = gen().constructor(PlayingCard.class, Suit.class, Rank.class).generate(random, status);
        }
        return new Hand(playingCards);
    }
}
