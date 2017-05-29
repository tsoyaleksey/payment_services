package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.ProviderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code ProviderEditPageAction} returns admin on edit-page
 * where admin can edit provider
 *
 */
public class ProviderEditPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();
        ProviderService providerService = new ProviderService();

        int id = Integer.parseInt(req.getParameter(ActionConstants.ID));

        req.setAttribute(ActionConstants.PROVIDERS_REGIONS, providerService.findAllRegionsByProvider(id));
        req.setAttribute(ActionConstants.PROVIDER, providerService.findProviderById(id));
        req.setAttribute(ActionConstants.LIST_OF_PROVIDERS, providerService.getListOfProviders());
        req.setAttribute(ActionConstants.LIST_OF_CATEGORIES, service.getListOfCategories());
        req.setAttribute(ActionConstants.LIST_OF_REGIONS, service.getListOfRegions());

        return new ActionResult(ActionConstants.EDIT_PROVIDER_PAGE);
    }
}