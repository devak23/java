package com.ak.learning.core.connectionpool.tasks;

import com.ak.learning.core.connectionpool.connection.DBConnection;
import com.ak.learning.core.connectionpool.pool.ConnectionPool;

import java.util.concurrent.TimeUnit;

public class StoredProcTask implements Runnable {
    private final ConnectionPool pool;
    private boolean completed;

    public StoredProcTask(ConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        System.out.println("StoredProc task requesting connection from the pool...");
        DBConnection connection = (DBConnection) pool.getConnection();
        System.out.println("Running a stored Proc using connection: " + connection);
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done executing Stored proc");
        pool.release(connection);
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
