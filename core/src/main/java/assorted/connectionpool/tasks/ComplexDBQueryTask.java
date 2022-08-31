package assorted.connectionpool.tasks;

import assorted.connectionpool.connection.DBConnection;
import assorted.connectionpool.pool.ConnectionPool;

import java.util.concurrent.TimeUnit;

public class ComplexDBQueryTask implements Runnable {
    private final ConnectionPool pool;
    private boolean completed;

    public ComplexDBQueryTask(ConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        System.out.println("ComplexDBQuery task requesting connection from the pool...");
        DBConnection connection = (DBConnection) pool.getConnection();
        System.out.println("Executing a Complex using connection " + connection);
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done executing complex DBQueryTask");
        pool.release(connection);
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
