package assorted.connectionpool.pool;

import assorted.connectionpool.connection.DBConnection;

import java.sql.Connection;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class DBConnectionPoolNonSynchronized implements ConnectionPool {
    private static final DBConnectionPoolNonSynchronized INSTANCE = new DBConnectionPoolNonSynchronized();
    private Queue<DBConnection> queue;
    private int MAX_COUNT;
    private AtomicInteger available;
    private static final Object monitor = new Object();
    private Lock dbLock = new ReentrantLock(true);
    private Condition connectionPoolNotEmpty = dbLock.newCondition(); // for all readers
    private Condition connectionPoolNotFull = dbLock.newCondition(); // for all writers

    private DBConnectionPoolNonSynchronized() {
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

    public boolean isAvailable() {
        return available.get() <= MAX_COUNT;
    }

    public Connection getConnection() {
        try {
            dbLock.lock();
            while (queue.isEmpty()) {
                try {
                    connectionPoolNotEmpty.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            DBConnection connection = queue.remove();
            connection.setState(DBConnection.STATE.OPEN);
            available.decrementAndGet();
            System.out.println("Returning one connection. Available connections = " + available.get());
            connectionPoolNotFull.signal();
            return connection;
        } finally {
            dbLock.unlock();
        }
    }

    public void release(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            dbLock.lock();
            while (queue.size() == MAX_COUNT) {
                try {
                    connectionPoolNotFull.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            ((DBConnection)connection).setState(DBConnection.STATE.CLOSED);
            queue.add((DBConnection)connection);
            available.incrementAndGet();
            System.out.println("Returning the connection to the pool. Available connections = " + available.get());
            connectionPoolNotEmpty.signal();
        } finally {
            dbLock.unlock();
        }
    }

    public static DBConnectionPoolNonSynchronized getInstance() {
        return INSTANCE;
    }
}
