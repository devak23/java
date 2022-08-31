package assorted.connectionpool.factory;

import assorted.connectionpool.ResourceType;
import assorted.connectionpool.pool.ConnectionPool;
import assorted.connectionpool.pool.DBConnectionPoolNonSynchronized;


public enum ConnectionPoolFactory {
    INSTANCE;

    public ConnectionPool getConnectionPool(ResourceType type) {
        return this.getConnectionPool(type, 20);
    }

    public ConnectionPool getConnectionPool(ResourceType type, int numberOfConnections) {
        switch (type) {
            case FILE:
                //return new FileConnectionPool();
                break;
            case DATABASE:
                DBConnectionPoolNonSynchronized instance = DBConnectionPoolNonSynchronized.getInstance();
//                DBConnectionPool instance = DBConnectionPool.getInstance();
                instance.initializePool(numberOfConnections);
                return instance;
            case NETWORK:
                //return new NetworkConnectionPool();
                break;
        }
        throw new IllegalArgumentException();
    }
}
