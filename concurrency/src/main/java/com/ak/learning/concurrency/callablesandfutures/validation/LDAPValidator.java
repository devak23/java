package com.ak.learning.concurrency.callablesandfutures.validation;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LDAPValidator implements Validator {
    private String name;

    public LDAPValidator(String name) {
        this.name = name;
    }

    @Override
    public boolean isValid(User user) {
        System.out.printf("%s: Validating %s against LDAP\n", this.getName(), user.getUsername());
        Random random = new Random();
        long duration = (long) (Math.random() * 10);
        System.out.printf("%s: Validating user for duration %d seconds\n", this.getName(), duration);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return random.nextBoolean();
    }

    @Override
    public String getName() {
        return name;
    }
}
