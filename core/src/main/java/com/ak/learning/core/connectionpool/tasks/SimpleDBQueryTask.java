package com.ak.learning.core.connectionpool.tasks;

import com.ak.learning.core.connectionpool.connection.DBConnection;
import com.ak.learning.core.connectionpool.pool.ConnectionPool;

import java.util.concurrent.TimeUnit;

public class SimpleDBQueryTask implements Runnable {
    private final ConnectionPool pool;
    private boolean completed;

    public SimpleDBQueryTask(ConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        System.out.println("SimpleDBQuery task requesting connection from the pool...");
        DBConnection connection = (DBConnection) pool.getConnection();
        System.out.println("Executing a Query using connection " + connection);
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done executing Query");
        pool.release(connection);
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
