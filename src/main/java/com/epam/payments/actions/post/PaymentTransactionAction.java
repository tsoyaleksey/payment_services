package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.PaymentHistory;
import com.epam.payments.models.Provider;
import com.epam.payments.models.User;
import com.epam.payments.services.ProviderService;
import com.epam.payments.services.UserService;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * {@code PaymentTransactionAction} allows user to pay for some provider
 * and then stores this transaction in payment history.
 */
public class PaymentTransactionAction implements Action {
    private static final Logger log = Logger.getLogger(PaymentTransactionAction.class);

    private static final int ZERO = 0;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        ProviderService service = new ProviderService();
        PaymentHistory paymentHistory = new PaymentHistory();

        int userId          = Integer.parseInt(String.valueOf(req.getSession().getAttribute(ActionConstants.USER_ID)));
        int providerId      = Integer.parseInt(req.getParameter(ActionConstants.PROVIDER_ID));
        String number       = req.getParameter(ActionConstants.NUMBER);
        String phoneNumber  = req.getParameter(ActionConstants.PHONE_NUMBER);
        Double sum          = Double.valueOf(req.getParameter(ActionConstants.SUM));

        try {
            if (userId != ZERO && providerId != ZERO && sum != ZERO) {
                User user = userService.findUserById(userId);
                Provider provider = service.findProviderById(providerId);

                if (number != null) {
                    paymentHistory.setNumber(number);
                } else if (phoneNumber != null) {
                    paymentHistory.setNumber(phoneNumber);
                }
                paymentHistory.setDateTime(LocalDateTime.now());
                paymentHistory.setProvider(provider);
                paymentHistory.setUser(user);

                if (sum <= user.getWallet().getBalance()) {
                    userService.subtractFromBalance(user, sum);
                    paymentHistory.setSum(sum);
                    req.getSession().setAttribute(ActionConstants.USER_BALANCE, user.getWallet().getBalance() - sum);
                } else {
                    req.setAttribute(ActionConstants.BALANCE_ERROR, true);
                    return new ActionResult(ActionConstants.PROVIDER_PAGE);
                }
                req.setAttribute(ActionConstants.SUCCESS, true);
                req.setAttribute(ActionConstants.DATE_OF_PAYMENT, paymentHistory.getDateTime());
                req.setAttribute(ActionConstants.NUMBER, paymentHistory.getNumber());
                req.setAttribute(ActionConstants.PROVIDER_NAME, provider.getName());
                req.setAttribute(ActionConstants.SUM, sum);
                userService.createNewRecordInPaymentHistory(paymentHistory);
                log.info("User: " + user.getName() + " made a payment of " + sum + " for " + provider.getName());
            }
        } catch (ServiceException e) {
            log.error("Cannot to do transaction ", e);
        }
        return new ActionResult(ActionConstants.PROVIDER_PAGE);
    }
}