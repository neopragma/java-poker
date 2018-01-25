package com.neopragma.poker.generators;

import com.neopragma.poker.Suit;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class Suits extends Generator<Enum> {
    private final Class<Suit> enumType;

    public Suits(Class<Suit> enumType) {
        super(Enum.class);

        this.enumType = enumType;
    }

    @Override public Suit generate(SourceOfRandomness random, GenerationStatus status) {
        Object[] values = enumType.getEnumConstants();
        int index = status.attempts() % values.length;
        return (Suit) values[index];
    }

    @Override public boolean canShrink(Object larger) {
        return enumType.isInstance(larger);
    }
}
