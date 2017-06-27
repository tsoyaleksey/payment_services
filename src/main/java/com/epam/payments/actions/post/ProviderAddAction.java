package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Category;
import com.epam.payments.models.Provider;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.ProviderService;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@code ProviderAddAction} allows admin add new provider
 */
public class ProviderAddAction implements Action {
    private static final Logger log = Logger.getLogger(ProviderAddAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ProviderService providerService = new ProviderService();
        ManagementService service       = new ManagementService();
        Provider provider;

        String name         = req.getParameter(ActionConstants.PROVIDER_NAME);
        String categoryId   = req.getParameter(ActionConstants.CATEGORY_ID);

        for (Provider p : providerService.getListOfProviders()) {
            if (p.getName().equals(name)) {
                req.setAttribute(ActionConstants.PROVIDER_EDIT_ERROR, ActionConstants.TRUE);
                return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE);
            }
        }

        try {
            if (name != null && categoryId != null) {
                Part part = req.getPart(ActionConstants.PROVIDERS_LOGOTYPE);
                InputStream logo = part.getInputStream();

                Category category = service.findCategoryById(Integer.parseInt(categoryId));
                provider = new Provider();
                provider.setCategory(category);
                provider.setLogotype(logo);
                provider.setName(name);
                providerService.createNewProvider(provider);
            } else {
                req.setAttribute(ActionConstants.PROVIDER_EDIT_ERROR, ActionConstants.TRUE);
                return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE);
            }
            log.info("Created new provider " + name + " ,category is " + provider.getCategory().getName());
        } catch (IOException | ServletException | ServiceException e) {
            req.setAttribute(ActionConstants.PROVIDER_EDIT_ERROR, ActionConstants.TRUE);
            log.error("Cannot add new provider ", e);
            return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE);
        }
        return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE, ActionConstants.isRedirect);
    }
}