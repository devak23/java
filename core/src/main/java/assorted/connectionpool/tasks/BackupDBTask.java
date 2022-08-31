package assorted.connectionpool.tasks;

import assorted.connectionpool.connection.DBConnection;
import assorted.connectionpool.pool.ConnectionPool;

import java.util.concurrent.TimeUnit;

public class BackupDBTask implements Runnable {
    private final ConnectionPool pool;
    private boolean completed;

    public BackupDBTask(ConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        System.out.println("Backup task requesting connection from the pool...");
        DBConnection connection = (DBConnection) pool.getConnection();
        System.out.println("Running a DB Backup Task with connection: " + connection);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done data backup");
        pool.release(connection);
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
