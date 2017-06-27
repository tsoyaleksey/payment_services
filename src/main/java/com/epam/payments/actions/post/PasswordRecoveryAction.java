package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.User;
import com.epam.payments.services.UserService;
import com.epam.payments.services.exception.ServiceException;
import com.epam.payments.utils.PasswordEncoder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

/**
 * {@code PasswordRecoveryAction} allows user to recover his password
 */
public class PasswordRecoveryAction implements Action {
    private static final Logger log = Logger.getLogger(PasswordRecoveryAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService service     = new UserService();

        String number           = req.getParameter(ActionConstants.PHONE_NUMBER);
        LocalDate dateOfBirth   = LocalDate.parse(req.getParameter(ActionConstants.DATE_OF_BIRTH));
        String password         = req.getParameter(ActionConstants.PASSWORD);
        String confirmPassword  = req.getParameter(ActionConstants.CONFIRM_PASSWORD);

        try {
            User user = service.findUserByNumber(number);

            if (user != null) {
                if (user.getDateOfBirth().equals(dateOfBirth)) {
                    if (confirmPassword.equals(password)) {
                        user.setPassword(PasswordEncoder.encode(password));
                        service.updateUserPassword(user);

                        HttpSession session = req.getSession();
                        session.setAttribute(ActionConstants.USER_ID, user.getId());
                        session.setAttribute(ActionConstants.USER_ROLE, user.getRole().getName());
                        session.setAttribute(ActionConstants.USER_REGION, user.getRegion().getName());
                        session.setAttribute(ActionConstants.REGION_ID, user.getRegion().getId());
                        session.setAttribute(ActionConstants.NAME, user.getName());
                        session.setAttribute(ActionConstants.USER_NUMBER, number);
                        session.setAttribute(ActionConstants.USER_BALANCE, user.getWallet().getBalance());
                        log.info("User with id: " + user.getId() + ", number: " + number + ", role: " + user.getRole().getName() + " logged in");
                        return new ActionResult(ActionConstants.MAIN_PAGE, ActionConstants.isRedirect);
                    }
                }
            }
            log.warn("User with number: " + number + ", with set date: " + dateOfBirth + " trying to recover password, but failed!");
        } catch (ServiceException e) {
            log.error("Cannot to do a password recovery ", e);
        }
        return new ActionResult(ActionConstants.PASSWORD_RECOVERY_PAGE);
    }
}
