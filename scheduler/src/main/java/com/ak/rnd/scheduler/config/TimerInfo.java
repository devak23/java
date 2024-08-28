package com.ak.rnd.scheduler.config;

import lombok.Builder;

import java.io.Serializable;

@Builder
//~ 3!
public record TimerInfo<T> (
    int totalFireCount,
    boolean runForever,
    long repeatIntervalMs,
    long initialOffsetMs,
    T callbackData
) implements Serializable {
}
