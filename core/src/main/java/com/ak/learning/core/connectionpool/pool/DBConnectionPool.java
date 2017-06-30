package com.ak.learning.core.connectionpool.pool;

import com.ak.learning.core.connectionpool.connection.DBConnection;

import java.sql.Connection;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class DBConnectionPool implements ConnectionPool {
    private static final DBConnectionPool INSTANCE = new DBConnectionPool();
    private final Object monitor = new Object();
    private Queue<DBConnection> queue;
    private int MAX_COUNT;
    private AtomicInteger available;

    public boolean isAvailable() {
        return available.get() <= MAX_COUNT;
    }

    private DBConnectionPool() {
    }

    public void initializePool(int numberOfConnections) {
        MAX_COUNT = numberOfConnections;
        queue = new ArrayBlockingQueue<>(numberOfConnections);
        available = new AtomicInteger(numberOfConnections);

        System.out.println("initializing the pool of " + MAX_COUNT + " connections");
        for (int i = 0; i < MAX_COUNT; i++) {
            queue.offer(new DBConnection());
        }
    }

    public Connection getConnection() {
        DBConnection connection = null;
        synchronized (monitor) {
            while (queue.isEmpty()) {
                try {
                    System.out.println("..... waiting");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            connection = queue.remove();
            connection.setState(DBConnection.STATE.OPEN);
            available.decrementAndGet();
            System.out.println("Returning one connection. Available connections = " + available.get());
            monitor.notifyAll();
        }
        return connection;
    }

    public void release(Connection connection) {
        if (connection == null) {
            return;
        }

        synchronized (monitor) {
            while (queue.size() == MAX_COUNT) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            ((DBConnection)connection).setState(DBConnection.STATE.CLOSED);
            queue.offer((DBConnection)connection);
            available.incrementAndGet();
            System.out.println("Returning the connection to the pool. Available connections = " + available.get());
            monitor.notifyAll();
        }
    }

    public static DBConnectionPool getInstance() {
        return INSTANCE;
    }
}
