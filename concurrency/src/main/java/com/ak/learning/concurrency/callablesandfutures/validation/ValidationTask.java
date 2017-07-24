package com.ak.learning.concurrency.callablesandfutures.validation;

import java.util.concurrent.Callable;

/**
 * This class is just a wrapper object that encapsulates the information to be passed on
 * to the Validator class. It implements the Callable interface as it can return the user
 * that got validated.
 */
public class ValidationTask implements Callable<String> {
    private Validator validator;
    private User user;

    public ValidationTask(Validator validator, User user) {
        this.validator = validator;
        this.user = user;
    }

    @Override
    public String call() throws Exception {
        if (!validator.isValid(user)) {
            System.out.printf("%s: The user was not found\n", validator.getName());
            throw new Exception("Error Validating User");
        }
        System.out.printf("%s: User was successfully validated\n", validator.getName());

        return validator.getName();
    }
}
