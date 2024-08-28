package com.ak.rnd.scheduler.config;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//~ 3!
public class TimerInfo<T> implements Serializable {
    private int totalFireCount;
    private int remainingFireCount;
    private boolean runForever;
    private long repeatIntervalMs;
    private long initialOffsetMs;
    private T callbackData;
}
