package com.ak.rnd.springevents.util;

import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomPerson;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static Person getPerson() {
        return new RandomPerson().next();
    }

    public static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(new Random(17).nextInt(1,8));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
