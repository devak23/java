package com.ak.reactive.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

/**
 * We can generate a Flux publisher using the range API provided.
 */
public class Lec05FluxRange {

    // Note that this can be used as a for-loop in many cases.
    public static void main(String[] args) {
        Flux.range(3, 10).subscribe(Util.onNext());
    }
}
