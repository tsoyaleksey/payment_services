package com.epam.payments.servlet;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionFactory;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.services.ManagementService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code FrontController} is required to works with all application requests
 * and responses except connected.
 */
@MultipartConfig(maxFileSize = 104_857_600)
public class FrontController extends HttpServlet {
    private static final Logger log = Logger.getLogger(FrontController.class);

    private static final String NOT_FOUND = "Not found";

    private static final String JSP_PATH = "/WEB-INF/jsp/";

    private static final String JSP_FORMAT = ".jsp";

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory = new ActionFactory();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = actionFactory.getAction(req);
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, NOT_FOUND);
        }
        doForwardOrRedirect(action.execute(req, resp), req, resp);
    }

    private void doForwardOrRedirect(ActionResult result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result.isRedirect()) {
            resp.sendRedirect(result.getView());
        } else {
            String path = String.format(JSP_PATH + result.getView() + JSP_FORMAT);
            ManagementService service = new ManagementService();
            if (req.getSession().getAttribute(ActionConstants.LANG) == null)
                req.getSession().setAttribute(ActionConstants.LANG, req.getLocale().getLanguage());
            req.setAttribute(ActionConstants.LIST_OF_REGIONS, service.getListOfRegions());
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }
}