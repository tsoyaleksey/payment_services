package com.epam.payments.connection.exception;

/**
 * Class covers all exceptions have in Connection pool.
 *
 * @author Aleksey Tsoy
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }
}
