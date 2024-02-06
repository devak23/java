package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * Transform, as the name suggests transforms your flux content to something that you want.
 */
public class Lec10Transform {

    // First we create a flux of people
    public static Flux<Person> getPeople() {
        return Flux.range(1, 10)
                .map(i -> new Person());
    }

    // Then we define a Function which will transform our flux items. It accepts a Flux<Person> and returns a
    // Flux<Person>. In that, we first filter the person by selecting all having age > 10 and then change the case
    // of their name (simulating a transformation)
    public static Function<Flux<Person>, Flux<Person>> applyFilterMap() {
        return personFlux -> personFlux
                .filter(p -> p.getAge() > 10)
                .doOnNext(p -> p.setName(p.getName().toUpperCase()))
                .doOnDiscard(Person.class, p -> System.out.println("Not allowing:" + p))
                ;
    }

    // Main
    public static void main(String[] args) {
        // So we get the people
        getPeople()
                // apply the transform by passing in the Transformation logic.
                .transform(applyFilterMap())
                // and print out the contents.
                .subscribe(Util.getSubscriber());

    }
}
