package assorted.connectionpool;

import assorted.connectionpool.factory.ConnectionPoolFactory;
import assorted.connectionpool.pool.ConnectionPool;
import assorted.connectionpool.tasks.*;
import com.ak.learning.core.connectionpool.tasks.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClient {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPoolFactory.INSTANCE.getConnectionPool(ResourceType.DATABASE, 2);
        ExecutorService requests = Executors.newFixedThreadPool(3);

        SimpleDBQueryTask task1 = new SimpleDBQueryTask(pool);
        SimpleFunctionTask task2 = new SimpleFunctionTask(pool);
        ComplexFunctionTask task3 = new ComplexFunctionTask(pool);
        ComplexDBQueryTask task4 = new ComplexDBQueryTask(pool);
        StoredProcTask task5 = new StoredProcTask(pool);
        BackupDBTask task6 = new BackupDBTask(pool);

        requests.submit(task1);
        requests.submit(task2);
        requests.submit(task3);
        requests.submit(task4);
        requests.submit(task5);
        requests.submit(task6);
        requests.shutdown();
    }
}
