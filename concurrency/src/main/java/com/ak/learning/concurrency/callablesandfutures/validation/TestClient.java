package com.ak.learning.concurrency.callablesandfutures.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this program creates two validators and validates the user from both in a multi-threaded way using
 * Executor framework
 */
public class TestClient {

    public static void main(String[] args) {
        // username and password to be validated
        String username = "test";
        String password = "test";

        // Create two validators. One that connects to LDAP
        LDAPValidator ldapValidator = new LDAPValidator("LDAP Validator");
        // ... and the other connects to DB
        DBValidator dbValidator = new DBValidator("DB Validator");

        // Create two tasks for each validator and add them to a list
        ValidationTask ldapTask = new ValidationTask(ldapValidator, new User(username, password));
        ValidationTask dbTask = new ValidationTask(dbValidator, new User(username, password));
        List<ValidationTask> taskList = new ArrayList<>(2);
        taskList.add(ldapTask);
        taskList.add(dbTask);

        // Create a CachedThreadPool
        ExecutorService pool = Executors.newCachedThreadPool();
        String result = null;

        try {
            /* The invokeAny() executes the given tasks, returning the result of one that has completed successfully
             * (i.e., without throwing an exception), if any do. Upon normal or exceptional return, tasks that
             * have not completed are cancelled.
             */
            result = pool.invokeAny(taskList);
            System.out.printf("Main: Result = %s\n", result);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }

        pool.shutdown();
        System.out.println("Terminating program.");
    }
}
