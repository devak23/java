package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec04LimitRate {

    public static void main(String[] args) {
        Flux.range(1, 1000)
                .log()
                //by default when the items consumed are 75% of the requested load, it will make a fresh call. If lowTide
                // parameter is set, the consumption is throttled at 60%
                .limitRate(100, 60)
                .subscribe(Util.getSlowSubscriber());
    }
}
