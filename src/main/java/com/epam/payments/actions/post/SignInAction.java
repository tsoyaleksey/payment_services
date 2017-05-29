package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.User;
import com.epam.payments.services.UserService;
import com.epam.payments.utils.PasswordEncoder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@code SignInAction} allows user to make sign in
 *
 */
public class SignInAction implements Action {
    private static final Logger log = Logger.getLogger(SignInAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService     = new UserService();

        String number               = req.getParameter(ActionConstants.PHONE_NUMBER);
        String password             = req.getParameter(ActionConstants.PASSWORD);

        User user = userService.findUserByNumberAndPassword(number, PasswordEncoder.encode(password));

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute(ActionConstants.USER_ID, user.getId());
            session.setAttribute(ActionConstants.USER_ROLE, user.getRole().getName());
            session.setAttribute(ActionConstants.USER_REGION, user.getRegion().getName());
            session.setAttribute(ActionConstants.REGION_ID, user.getRegion().getId());
            session.setAttribute(ActionConstants.NAME, user.getName());
            session.setAttribute(ActionConstants.USER_NUMBER, number);
            session.setAttribute(ActionConstants.USER_BALANCE, user.getWallet().getBalance());
        } else {
            req.setAttribute(ActionConstants.USER_ERROR, true);
            return new ActionResult(ActionConstants.SIGN_IN_PAGE);
        }
        log.info("User with id: " + user.getId() + ", number: " + number + ", role: " + user.getRole().getName() + " logged in");
        return new ActionResult(ActionConstants.MAIN_PAGE, true);
    }
}