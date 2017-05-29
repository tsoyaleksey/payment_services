package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Provider;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.ProviderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code ProviderPageAction} returns user on provider.jsp
 * by provider id
 */
public class ProviderPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ProviderService providerService = new ProviderService();
        ManagementService service       = new ManagementService();

        int regionId = Integer.parseInt(String.valueOf(req.getSession().getAttribute(ActionConstants.REGION_ID)));
        int id = Integer.parseInt(req.getParameter(ActionConstants.ID));

        req.setAttribute(ActionConstants.LIST_OF_PROVIDERS, providerService.findAllProvidersByRegion(regionId));
        req.setAttribute(ActionConstants.LIST_OF_CATEGORIES, service.getListOfCategories());

        if (id != 0) {
            Provider provider = providerService.findProviderById(id);
            if (provider == null) {
                return new ActionResult(ActionConstants.ERROR);
            }
            req.setAttribute(ActionConstants.PROVIDER, provider);
        }
        return new ActionResult(ActionConstants.PROVIDER_PAGE);
    }
}