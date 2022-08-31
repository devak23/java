package assorted.connectionpool.pool;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
    void release(Connection connection);
}
