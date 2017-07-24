package com.ak.learning.concurrency.callablesandfutures.validation;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class holds the business logic of actually validating the user
 */
public class DBValidator implements Validator {
    private String name;

    public DBValidator(String name) {
        this.name = name;
    }

    public boolean isValid(User user) {
        System.out.printf("%s: Validating %s against the database\n", this.getName(), user.getUsername());
        Random random = new Random();
        long duration = (long)(Math.random() * 10);
        System.out.printf("%s: Validating the user during %d seconds\n", this.name, duration);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        return random.nextBoolean();
    }

    public String getName() {
        return name;
    }
}
