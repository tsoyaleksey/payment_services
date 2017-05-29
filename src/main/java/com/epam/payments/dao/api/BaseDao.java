package com.epam.payments.dao.api;

import java.sql.Connection;

/**
 * The base class {@code BaseDao} is designed
 * for all classes of heirs.
 *
 * @author Aleksey Tsoy
 */
public abstract class BaseDao {
    private Connection connection;

    /**
     * The method allows to get rid of a duplicate code
     * in Dao entities.
     *
     * @return current connection
     */
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
