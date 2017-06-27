package com.epam.payments.services.exception;

/**
 * Class covers all exceptions have in Services.
 *
 * @author Aleksey Tsoy
 */
public class ServiceException extends Exception {
    public ServiceException(String message, Exception e) {
        super(message, e);
    }

    public ServiceException(String message) {
        super(message);
    }
}
