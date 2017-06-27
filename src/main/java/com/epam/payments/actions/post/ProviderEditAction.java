package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Category;
import com.epam.payments.models.Provider;
import com.epam.payments.models.Region;
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
import java.util.ArrayList;
import java.util.List;

/**
 * {@code ProviderEditAction} takes all info from edit-provider.jsp
 * and then update provider if set info was new. Also this class
 * allows set region(s) for provider.
 */
public class ProviderEditAction implements Action {
    private static final Logger log = Logger.getLogger(ProviderEditAction.class);

    private ProviderService providerService = new ProviderService();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();
        List<Region> listOfRegions;

        String name       = req.getParameter(ActionConstants.PROVIDER_NAME);
        String[] regions  = req.getParameterValues(ActionConstants.REGION_ID);
        String providerId = req.getParameter(ActionConstants.PROVIDER_ID);
        String categoryId = req.getParameter(ActionConstants.CATEGORY_ID);

        try {
            if (providerId != null && categoryId != null && regions != null) {
                Part part        = req.getPart(ActionConstants.NEW_LOGOTYPE);
                InputStream logo = part.getInputStream();

                listOfRegions     = new ArrayList<>();
                Provider provider = providerService.findProviderById(Integer.parseInt(providerId));
                Category category = service.findCategoryById(Integer.parseInt(categoryId));

                for (String regionId : regions) {
                    Region region = service.findRegionById(Integer.parseInt(regionId));
                    Region foundRegion = providerService.findByProviderAndRegion(provider, region);
                    if (foundRegion == null || region.equals(foundRegion)) listOfRegions.add(region);
                }

                providerService.updateProvider(editProvidersData(provider,category,name,logo), listOfRegions);
            } else {
                req.setAttribute(ActionConstants.PROVIDER_EDIT_ERROR, ActionConstants.TRUE);
                return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE);
            }
        } catch (ServletException | IOException | ServiceException e) {
            log.error("Cannot edit provider ", e);
            req.setAttribute(ActionConstants.PROVIDER_EDIT_ERROR, ActionConstants.TRUE);
            return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE);
        }
        return new ActionResult(ActionConstants.EDIT_PROVIDERS_PAGE, ActionConstants.isRedirect);
    }

    private Provider editProvidersData(Provider provider, Category category, String name, InputStream logo) throws ServiceException {
        provider.setCategory(category);
        provider.setLogotype(logo);

        for (Provider p : providerService.getListOfProviders()) {
            if (!p.getName().equals(name) || p.getName().equals(provider.getName())) {
                provider.setName(name);
            } else {
                throw new ServiceException("Cannot edit provider");
            }
        }
        return  provider;
    }
}