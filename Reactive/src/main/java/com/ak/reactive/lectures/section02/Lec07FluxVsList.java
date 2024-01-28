package com.ak.reactive.lectures.section02;

import com.ak.reactive.lectures.section02.helper.NameGenerator;
import com.ak.reactive.utils.Util;

import java.util.List;

/**
 * We will compare in this example how a Flux can provide a better client experience compared to the traditional way
 * of handling the data and its processing by the client
 */
public class Lec07FluxVsList {

    public static void main(String[] args) {
        // This implementation will pause for 5 seconds before the list is received and processed by the client
        System.out.println("****** Traditional List processing ******");
        List<String> names = NameGenerator.getNamesTraditional(5);
        System.out.println(names);

        // Same processing of the data elements (in the list) but with Flux
        System.out.println("****** Flux data processing ******");
        NameGenerator.getNames(5).subscribe(Util.onNext());

        // You can clearly see that in the second case (above invocation) the client frustration is much less than the
        // traditional one. We need to understand the difference here is that List is a data structure but Flux isn't
        // However, in this particular example, the intent is NOT to compare datastructures but rather the client
        // experience.
    }
}
