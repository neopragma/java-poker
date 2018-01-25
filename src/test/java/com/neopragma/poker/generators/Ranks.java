package com.neopragma.poker.generators;

import com.neopragma.poker.Rank;
import com.neopragma.poker.Result;
import com.neopragma.poker.Suit;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class Ranks extends Generator<Enum> {
    private final Class<Rank> enumType;

    public Ranks(Class<Rank> enumType) {
        super(Enum.class);

        this.enumType = enumType;
    }

    @Override public Rank generate(SourceOfRandomness random, GenerationStatus status) {
        Object[] values = enumType.getEnumConstants();
        int index = status.attempts() % values.length;
        return (Rank) values[index];
    }

    @Override public boolean canShrink(Object larger) {
        return enumType.isInstance(larger);
    }
}
