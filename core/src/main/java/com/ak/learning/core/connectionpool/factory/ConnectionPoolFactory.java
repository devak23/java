package com.ak.learning.core.connectionpool.factory;

import com.ak.learning.core.connectionpool.ResourceType;
import com.ak.learning.core.connectionpool.pool.ConnectionPool;
import com.ak.learning.core.connectionpool.pool.DBConnectionPoolNonSynchronized;


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
