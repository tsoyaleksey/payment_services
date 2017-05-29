package com.epam.payments.actions.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code Action} is necessary for creating actions.
 *
 * @author Aleksey Tsoy
 */
public interface Action {
    /**
     * Method are executes all requests and responses
     *
     * @param req
     * @param resp
     * @return
     */
    ActionResult execute(HttpServletRequest req, HttpServletResponse resp);
}
