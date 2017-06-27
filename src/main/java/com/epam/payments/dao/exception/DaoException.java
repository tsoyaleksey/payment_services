package com.epam.payments.dao.exception;

/**
 * Class covers all exceptions arose in Dao layer.
 *
 * @author Aleksey Tsoy
 */
public class DaoException extends Exception {

    public DaoException(String message, Exception e) {
        super(message, e);
    }

    public DaoException(String message) {
        super(message);
    }
}
