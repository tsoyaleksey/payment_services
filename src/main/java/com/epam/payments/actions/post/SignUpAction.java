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
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code SignUpAction} takes all information about new user and validates it.
 * If all information is correct then class create a new user.
 * If something goes wrong then class writes an information about wrong field in attribute.
 *
 * @author Aleksey Tsoy
 */
public class SignUpAction implements Action {
    private static final Logger log = Logger.getLogger(SignUpAction.class);

    private boolean isValid = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        User user               = new User();
        Properties properties   = new Properties();

        try {
            properties.load(SignUpAction.class.getClassLoader().getResourceAsStream(ActionConstants.VALIDATION_PROPERTIES));
        } catch (IOException e) {
            log.error("Cannot load properties ", e);
        }

        String phone            = req.getParameter(ActionConstants.PHONE_NUMBER);
        String name             = req.getParameter(ActionConstants.NAME);
        String dateOfBirth      = req.getParameter(ActionConstants.DATE_OF_BIRTH);
        String password         = req.getParameter(ActionConstants.PASSWORD);
        String confirmPassword  = req.getParameter(ActionConstants.CONFIRM_PASSWORD);

            if (userService.checkNumberAvailability(phone)) {
                checkParametersByRegex(phone, ActionConstants.PHONE_NUMBER, properties.getProperty(ActionConstants.PHONE_NUMBER_REGEX), req);
                checkParametersByRegex(name, ActionConstants.NAME, properties.getProperty(ActionConstants.NAME_REGEX), req);
                checkParametersByRegex(dateOfBirth, ActionConstants.DATE_OF_BIRTH, properties.getProperty(ActionConstants.DATE_OF_BIRTH_REGEX), req);

                if (password.equals(confirmPassword)) {
                    checkParametersByRegex(password, ActionConstants.PASSWORD, properties.getProperty(ActionConstants.PASSWORD_REGEX), req);
                } else {
                    req.setAttribute(ActionConstants.PASSWORD_ERROR, ActionConstants.TRUE);
                }
            } else {
                req.setAttribute(ActionConstants.NUMBER_ERROR, ActionConstants.NUMBER_ERROR_MSG);
                isValid = true;
            }

        if (isValid) {
            isValid = false;
            return new ActionResult(ActionConstants.SIGN_UP_PAGE);
        } else {
            try {
                user.setPhone(phone);
                user.setName(name);
                user.setDateOfBirth(LocalDate.parse(dateOfBirth));
                user.setPassword(PasswordEncoder.encode(password));
                userService.registerUser(user);
                log.info("New user are registered in the system " + user.getName() + " , phone " + user.getPhone() + " , date of birth " + user.getDateOfBirth());
            } catch (ServiceException e) {
                log.error("Cannot register ", e);
            }
        }
        return new ActionResult(ActionConstants.SIGN_IN_PAGE, ActionConstants.isRedirect);
    }

    private void checkParametersByRegex(String parameter, String paramName, String regex, HttpServletRequest req) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        if(!matcher.matches()) {
            req.setAttribute(paramName + ActionConstants.ERROR, ActionConstants.TRUE);
            isValid = true;
        }
    }
}