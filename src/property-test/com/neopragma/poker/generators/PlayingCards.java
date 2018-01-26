package com.neopragma.poker.generators;

import com.neopragma.poker.PlayingCard;
import com.neopragma.poker.Rank;
import com.neopragma.poker.Suit;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class PlayingCards extends Generator<PlayingCard> {
    public PlayingCards() {
        super(PlayingCard.class);
    }
    @Override
    public PlayingCard generate(SourceOfRandomness random, GenerationStatus status) {
        Suit suit = gen().type(Suit.class).generate(random, status);
        Rank rank = gen().type(Rank.class).generate(random, status);
        return new PlayingCard(suit, rank);
    }
}
