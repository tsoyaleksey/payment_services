package com.epam.payments.actions.api;

import com.epam.payments.actions.get.*;
import com.epam.payments.actions.post.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code ActionFactory} is necessary to initialize all actions.
 */
public class ActionFactory {

    private Map<String, Action> actions;

    public ActionFactory() {
        actions = new HashMap<>();

        actions.put("GET/sign-up", new ShowPageAction(ActionConstants.SIGN_UP_PAGE));
        actions.put("GET/sign-upAjax", new SignUpAjaxAction());
        actions.put("GET/sign-in", new ShowPageAction(ActionConstants.SIGN_IN_PAGE));
        actions.put("GET/passwordrecovery", new ShowPageAction(ActionConstants.PASSWORD_RECOVERY_PAGE));
        actions.put("GET/main", new MainPageAction());
        actions.put("GET/set-locale", new SetLocaleAction());
        actions.put("GET/set-region", new RegionSelectAction());
        actions.put("GET/payments", new PaymentsPageAction());
        actions.put("GET/edit-categories", new CategorySettingsPageAction());
        actions.put("GET/edit-regions", new RegionSettingsPageAction());
        actions.put("GET/edit-providers", new ProviderSettingsPageAction());
        actions.put("GET/edit-provider", new ProviderEditPageAction());
        actions.put("GET/logotype", new ProvidersLogotypeAction());
        actions.put("GET/provider", new ProviderPageAction());

        actions.put("POST/sign-up", new SignUpAction());
        actions.put("POST/sign-in", new SignInAction());
        actions.put("POST/passwordrecovery", new PasswordRecoveryAction());
        actions.put("POST/sign-out", new SignOutAction());
        actions.put("POST/top-up", new TopUpBalanceAction());
        actions.put("POST/edit-category", new CategoryEditAction());
        actions.put("POST/add-category", new CategoryAddAction());
        actions.put("POST/edit-region", new RegionEditAction());
        actions.put("POST/add-region", new RegionAddAction());
        actions.put("POST/add-provider", new ProviderAddAction());
        actions.put("POST/edit-provider", new ProviderEditAction());
        actions.put("POST/payment-transaction", new PaymentTransactionAction());
    }

    public Action getAction(HttpServletRequest req) {
        return actions.get(req.getMethod() + req.getPathInfo());
    }
}
