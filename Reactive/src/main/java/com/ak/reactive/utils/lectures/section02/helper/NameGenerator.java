package com.ak.reactive.utils.lectures.section02.helper;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    // The NameGenerator is a simulated long running process which fetches the data (names in this case) and invoking
    // such a source could cause a delay on the client side. Just like the 5 second delay caused by our infamous
    // NameGenerator.
    public static List<String> getNamesTraditional(int count) {
        List<String> names = new ArrayList<>(10);
        for (int i=0; i<count; i++) {
            names.add(getName());
        }

        return names;
    }

    // So how do we improve this client experience? We can use a Flux's Range method to get us names and transmit to
    // the client as and when they come. Note even in this case, we are using the base method getName() which is going
    // to generate a delay. There is no change in the actual data generation. However, the client does NOT have to wait
    // all the 5 seconds to consume the list as is the fate decided by the `getNamesTraditional()` API
    public static Flux<String> getNames(int count) {
        return Flux.range(0, count).map(i -> getName());
    }

    private static String getName() {
        Util.sleep(1);
        return Util.faker().name().fullName();
    }
}
