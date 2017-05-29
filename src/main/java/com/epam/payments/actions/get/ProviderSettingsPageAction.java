package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.ProviderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code ProviderSettingsPageAction} returns on admin page
 * where admin can add or edit providers.
 */
public class ProviderSettingsPageAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ProviderService providerService = new ProviderService();
        ManagementService service       = new ManagementService();

        req.setAttribute(ActionConstants.LIST_OF_PROVIDERS, providerService.getListOfProviders());
        req.setAttribute(ActionConstants.LIST_OF_CATEGORIES, service.getListOfCategories());
        req.setAttribute(ActionConstants.LIST_OF_REGIONS, service.getListOfRegions());

        return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE);
    }
}
