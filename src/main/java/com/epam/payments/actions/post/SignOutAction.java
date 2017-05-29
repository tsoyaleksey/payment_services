package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@code SignOutAction} allows user to make an sign out
 *
 */
public class SignOutAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return new ActionResult(ActionConstants.SIGN_IN_PAGE, true);
    }
}
