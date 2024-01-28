package com.ak.reactive.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFloat extends Number {
    private AtomicInteger integer = new AtomicInteger();

    public AtomicFloat() {
        this(0f);
    }

    public AtomicFloat(float initialValue) {
        integer = new AtomicInteger(Float.floatToIntBits(initialValue));
    }

    public final boolean compareAndSet(float expect, float update) {
        return integer.compareAndSet(Float.floatToIntBits(expect), Float.floatToIntBits(update));
    }

    public final boolean weakCompareAndSet(float expect, float update) {
        return integer.weakCompareAndSetPlain(Float.floatToIntBits(expect), Float.floatToIntBits(update));
    }

    public final float get() {
        return Float.intBitsToFloat(integer.get());
    }

    public final float getAndSet(float newValue) {
        return Float.intBitsToFloat(integer.getAndSet(Float.floatToIntBits(newValue)));
    }

    @Override
    public float floatValue() {
        return integer.get();
    }

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return (long) get();
    }


    @Override
    public double doubleValue() {
        return floatValue();
    }
}
