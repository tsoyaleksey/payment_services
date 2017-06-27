package com.epam.payments.listeners;

import com.epam.payments.connection.ConnectionPool;
import com.epam.payments.connection.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * {@code WebAppListener} required to prepare all connections to the database when the App is started.
 *
 * @author Aleksey Tsoy
 */

@WebListener
public class WebAppListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(WebAppListener.class);

    private static final String POOL = "pool";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ConnectionPool pool = ConnectionPool.getInstance();
        servletContext.setAttribute(POOL, pool);
        log.info("Create a new instance of connection pool");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ConnectionPool pool = (ConnectionPool) servletContext.getAttribute(POOL);
        try {
            pool.closeAllConnections();
            log.info("All connections are closed");
        } catch (ConnectionPoolException e) {
            log.error("Cannot close all connections ", e);
        }
    }
}
