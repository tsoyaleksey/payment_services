package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.PaymentHistory;
import com.epam.payments.models.User;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.ProviderService;
import com.epam.payments.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * {@code PaymentsPageAction} returns user on payments.jsp
 */
public class PaymentsPageAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service       = new ManagementService();
        ProviderService providerService = new ProviderService();
        UserService userService         = new UserService();

        int regionId = Integer.parseInt(String.valueOf(req.getSession().getAttribute(ActionConstants.REGION_ID)));
        int userId   = Integer.parseInt(String.valueOf(req.getSession().getAttribute(ActionConstants.USER_ID)));

        User user = userService.findUserById(userId);
        List<PaymentHistory> paymentHistories = userService.findAllPaymentHistoryRecordsByUser(user);

        if (!paymentHistories.isEmpty()) {
            req.setAttribute(ActionConstants.LIST_OF_PAYMENT_HISTORY_RECORDS, paymentHistories);
        } else {
            req.setAttribute(ActionConstants.NO_DATA, true);
        }

        req.setAttribute(ActionConstants.LIST_OF_PROVIDERS, providerService.findAllProvidersByRegion(regionId));
        req.setAttribute(ActionConstants.LIST_OF_CATEGORIES, service.getListOfCategories());

        return new ActionResult(ActionConstants.PAYMENTS_PAGE);
    }
}
