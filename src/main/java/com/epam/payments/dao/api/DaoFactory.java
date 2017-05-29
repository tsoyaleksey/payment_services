package com.epam.payments.dao.api;

import com.epam.payments.connection.ConnectionPool;
import com.epam.payments.connection.exception.ConnectionPoolException;
import com.epam.payments.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * {@code DaoFactory} works with connections to the database
 * and gets dao with an open connection inside. Implements
 * {@link AutoCloseable} interface for auto closing connection
 * in try-with-resources block.
 *
 * @author Aleksey Tsoy
 */
public class DaoFactory implements AutoCloseable {
    private static final Logger log = Logger.getLogger(DaoFactory.class);

    private ConnectionPool connectionPool;

    private Connection connection;

    public DaoFactory() {
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            log.error("Cannot get connection from pool ", e);
        }
    }

    public <T extends BaseDao> T getDao(Class<T> clazz) throws DaoException {
        T t;
        try {
            t = clazz.newInstance();
            t.setConnection(connection);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Cannot make a new instance of Dao ", e);
            throw new DaoException("Cannot make a new instance of Dao ", e);
        }
        return t;
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("Cannot to start transaction ", e);
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Cannot to commit transaction ", e);
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
            log.debug("Rollback transaction ");
        } catch (SQLException e) {
            log.error("Cannot rollback transaction ", e);
        }
    }

    @Override
    public void close() {
        try {
            connectionPool.closeConnection(connection);
        } catch (ConnectionPoolException e) {
            log.error("Cannot close the connection ", e);
        }
    }
}
