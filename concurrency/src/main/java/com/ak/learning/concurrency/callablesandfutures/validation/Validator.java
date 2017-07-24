package com.ak.learning.concurrency.callablesandfutures.validation;

public interface Validator {
    boolean isValid(User user);
    String getName();
}
