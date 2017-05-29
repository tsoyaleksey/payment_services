package com.epam.payments.connection;

import com.epam.payments.connection.exception.ConnectionPoolException;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * {@code ConnectionPool} is designed to work with connections to the database.
 *
 * @author Tsoy Aleksey
 *
 */
public class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool;
    private BlockingQueue<Connection> freeConnections = null;
    private BlockingQueue<Connection> usedConnections = null;
    private String url;
    private String username;
    private String password;
    private String driver;
    private int connectionsLimit;

    private ConnectionPool() {
        try {
            loadDBProperties();
            init();
        } catch (ConnectionPoolException e) {
            log.error("Cannot create a new instance of connection pool ", e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    /**
     * The method performs loading of database properties.
     *
     * @throws ConnectionPoolException
     */
    private void loadDBProperties() throws ConnectionPoolException {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("database/database.properties"));
            log.info("Load the properties file with information about the DB");
        } catch (IOException e) {
            log.error("Cannot load properties ", e);
        }
        if (!properties.isEmpty()) {
            log.info("Set information about DB to an instance");
            setUrl(properties.getProperty("url"));
            setUsername(properties.getProperty("username"));
            setPassword(properties.getProperty("password"));
            setDriver(properties.getProperty("driver"));
            setConnectionsLimit(Integer.parseInt(properties.getProperty("connections.limit")));
        } else {
            log.error("Properties haven't got parameters");
        }
    }

    /**
     * The method initializes the fields and fills the connection pool.
     *
     * @throws ConnectionPoolException
     */
    private void init () throws ConnectionPoolException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("Could not find class ", e);
        }
        freeConnections = new ArrayBlockingQueue<>(connectionsLimit);
        usedConnections = new ArrayBlockingQueue<>(connectionsLimit);
        while (freeConnections.size() != connectionsLimit) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.put(connection);
            } catch (SQLException | InterruptedException e) {
                log.error("Cannot get connection or put in the connection queue ", e);
            }
        }
    }

    /**
     * The method returns a connection if at least one free connection is
     * in the "free connection queue", otherwise it expects for any free connection.
     *
     * @return free connection
     * @throws ConnectionPoolException
     */
    public Connection getConnection() throws ConnectionPoolException {
        Connection currentConnection = null;
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        try {
            currentConnection = freeConnections.take();
            usedConnections.put(currentConnection);
        } catch (InterruptedException e) {
            log.error("Cannot replace the connection ", e);
        }
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        return currentConnection;
    }

    /**
     * The method performs: closing connection and return it to the free connection queue.
     *
     * @param connection which should be closed and return it to the free queue.
     * @throws ConnectionPoolException
     */
    public void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            log.error("Could not close the connection ", e);
        }
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
    }

    private void closeAllConnectionInQueue(BlockingQueue<Connection> connections) throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Could not close the connection ", e);
            }
        }
    }

    /**
     * The method closing all connections to the database.
     *
     * @throws ConnectionPoolException
     */
    public void closeAllConnections() throws ConnectionPoolException {
        closeAllConnectionInQueue(freeConnections);
        closeAllConnectionInQueue(usedConnections);
        log.info("All connections are closed");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }
}