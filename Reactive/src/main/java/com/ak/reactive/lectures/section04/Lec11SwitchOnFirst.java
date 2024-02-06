package com.ak.reactive.lectures.section04;

import com.ak.reactive.lectures.section04.helper.Person;
import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.function.Function;

/**
 * This operator will check the condition for the first item and see if the next series of the elements require
 * transformation or not. This is similar to you tasting the ice-cream with a tasting spoon and then deciding if you
 * want to go for it.
 */
public class Lec11SwitchOnFirst {
    // So what are we doing here? We are first creating a Flux of people
    public static Flux<Person> getPeople() {
        return Flux.range(1, 30)
                .map(i -> new Person());
    }

    // Then what?  - We then define the transformation we want to apply should we find the "violating condition".
    // Our code below creates a Function that needs to be applied (passed on) to the Flux's switchOnFirst operator.
    // This is a function that takes in a Flux<Person> and returns a Flux<Person>
    public static Function<Flux<Person>, Flux<Person>> applyFilterMap() {
        // since we are returning a Function this has to be a lambda expression in which
        return personFlux -> personFlux
                // We first filter people of age > 10
                .filter(p -> p.getAge() > 10)
                // and if found, we will transform their names into upper case.
                .doOnNext(p -> p.setName(p.getName().toUpperCase()));
    }

    // We are now ready with our main program
    public static void main(String[] args) {
        // So we get our people
        getPeople()
                // invoke the switchOnFirst which accepts a BiFunction (signal, flux).
                .switchOnFirst((signal, personFlux) -> {
                    System.out.println("inside switchOnFirst..."); // this will be printed only once.

                    // We will first check if the signal isOnNext (basically it's a validation of the signal)
                    // and then followed by getting the age of the person in the flux.
                    // If this age is > 10, then we return the flux itself, otherwise...
                    return signal.isOnNext() && Objects.requireNonNull(signal.get()).getAge() > 10
                            ? personFlux
                            // We will apply our transformation logic defined above.
                            : applyFilterMap().apply(personFlux);
                })
                .subscribe(Util.getSubscriber());
    }
}
