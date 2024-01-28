package com.ak.reactive.utils.lectures.section01;

import com.ak.reactive.utils.Util;
import com.github.javafaker.Faker;
import reactor.core.publisher.Mono;

/**
 * So what happens if there is nothing emitted? Let's try it out...
 */
public class Lec04MonoEmptyOrError {

    /**
     * Let's create a userRepository that will give us users. We will leverage a "faker library" that will return us
     * a publisher of names if the id is "valid"
     *
     * @param userId that we will check in our list
     * @return a publisher (Mono) of names (String)
     */
    public static Mono<String> userRepository(int userId) {
        if (userId == 1) {
            return Mono.just(Faker.instance().name().fullName());
        } else if (userId == 2) {
            return Mono.empty();
        } else {
            return Mono.error(new RuntimeException("Invalid index. Can't look up name for this index"));
        }

        // So the business logic is simple. It will return a name only if the index passed is 1.
    }


    /**
     * Now let's try to use this userRepository in our main program
     */
    public static void main(String[] args) {
        // this will print the name as expected.
        System.out.println("***** userId = 1 *******");
        userRepository(1).subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        // This won't print anything
        System.out.println("***** userId = 2 *******");
        userRepository(2).subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        // This will print the exception message as expected.
        System.out.println("***** userId = 3 *******");
        userRepository(3).subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }

    // Let's move the faker into the Util class so we can use it from one place - Util.
}
