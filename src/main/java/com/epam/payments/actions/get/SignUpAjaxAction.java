package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * {@code SignUpAjaxAction} return on sign-up.jsp
 * response about free entered number or not.
 *
 */
public class SignUpAjaxAction implements Action {
    private static final Logger log = Logger.getLogger(SignUpAjaxAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        String number = req.getParameter(ActionConstants.PHONE_NUMBER);

        UserService service = new UserService();

        try (Writer writer = resp.getWriter()) {
            if (service.checkNumberAvailability(number)) {
                writer.write("<i class='glyphicon glyphicon-ok'></i>");
            } else {
                writer.write("<i class='glyphicon glyphicon-remove alert-danger'></i>");
            }
        } catch (IOException e) {
            log.error("Cannot get response writer ", e);
        }
        return new ActionResult(ActionConstants.SIGN_UP_PAGE);
    }
}