package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Region;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code RegionAddAction} allows admin add new region
 */
public class RegionAddAction implements Action {
    private static final Logger log = Logger.getLogger(RegionAddAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();

        String name = req.getParameter(ActionConstants.REGION_NAME);

        Region region = null;

        if (name != null) {
            try {
                for (Region r : service.getListOfRegions()) {
                    if (!r.getName().equals(name)) {
                        region = new Region();
                        region.setName(name);
                    } else {
                        req.setAttribute(ActionConstants.REGION_ERROR, true);
                        return new ActionResult(ActionConstants.EDIT_REGIONS_PAGE);
                    }
                }
                service.createNewRegion(region);
            } catch (ServiceException e) {
                log.error("Cannot add new region ", e);
            }
        }
        log.info("Added new region " + name);
        return new ActionResult(ActionConstants.EDIT_REGIONS_PAGE, true);
    }
}
