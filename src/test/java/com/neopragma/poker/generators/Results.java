package com.neopragma.poker.generators;

import com.neopragma.poker.Result;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class Results extends Generator<Enum> {
    private final Class<Result> enumType;

    public Results(Class<Result> enumType) {
        super(Enum.class);

        this.enumType = enumType;
    }

    @Override public Result generate(SourceOfRandomness random, GenerationStatus status) {
        Object[] values = enumType.getEnumConstants();
        int index = status.attempts() % values.length;
        return (Result) values[index];
    }

    @Override public boolean canShrink(Object larger) {
        return enumType.isInstance(larger);
    }
}
