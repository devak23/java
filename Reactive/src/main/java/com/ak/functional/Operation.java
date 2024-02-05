package com.ak.functional;

public interface Operation<T, R> {
    R apply(T arg);
}
