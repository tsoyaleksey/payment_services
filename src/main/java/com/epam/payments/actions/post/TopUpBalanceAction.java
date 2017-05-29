package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.User;
import com.epam.payments.services.UserService;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code TopUpBalanceAction} allows user to top up his balance.
 */
public class TopUpBalanceAction implements Action {
    private static final Logger log = Logger.getLogger(TopUpBalanceAction.class);

    private static final double MAX = 150000;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService service         = new UserService();
        double enteredBalance       = Double.parseDouble(req.getParameter(ActionConstants.BALANCE_FIELD));
        String userNumber           = String.valueOf(req.getSession().getAttribute(ActionConstants.USER_NUMBER));

        try {
            User user = service.findUserByNumber(userNumber);
            if (enteredBalance != 0 && enteredBalance <= MAX && user.getWallet().getBalance() + enteredBalance <= MAX) {
                service.toTopUpBalance(user, enteredBalance);
                req.getSession().setAttribute(ActionConstants.USER_BALANCE, user.getWallet().getBalance() + enteredBalance);
            } else {
                req.setAttribute(ActionConstants.TOP_UP_THE_BALANCE_ERROR, true);
                return new ActionResult(ActionConstants.MAIN_PAGE);
            }
            log.info("User: " + user.getPhone() + " , to top up his balance on " + enteredBalance);
        } catch (ServiceException e) {
            log.error("Cannot top up user's balance ", e);
        }
        return new ActionResult(req.getHeader(ActionConstants.REFERER), true);
    }
}
