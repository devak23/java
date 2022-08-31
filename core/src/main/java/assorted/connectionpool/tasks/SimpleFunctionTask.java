package assorted.connectionpool.tasks;

import assorted.connectionpool.connection.DBConnection;
import assorted.connectionpool.pool.ConnectionPool;

import java.util.concurrent.TimeUnit;

public class SimpleFunctionTask implements Runnable {
    private final ConnectionPool pool;
    private boolean completed;

    public SimpleFunctionTask(ConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        System.out.println("SimpleFunction task requesting connection from the pool...");
        DBConnection connection = (DBConnection) pool.getConnection();
        System.out.println("Executing a Simple Function using connection " + connection);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done executing simple function");
        pool.release(connection);
        completed = true;
    }
    
    public boolean isCompleted() {
        return completed;
    }
}
