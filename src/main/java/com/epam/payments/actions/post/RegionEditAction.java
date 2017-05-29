package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Region;
import com.epam.payments.services.ManagementService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code RegionEditAction} allows admin to edit region name
 */
public class RegionEditAction implements Action {
    private static final Logger log = Logger.getLogger(RegionEditAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();

        String name = req.getParameter(ActionConstants.REGION_NAME);
        int id      = Integer.parseInt(req.getParameter(ActionConstants.REGION_ID));

        if (name != null) {
            for (Region r : service.getListOfRegions()) {
                if (!r.getName().equals(name)) {
                    Region region = service.findRegionById(id);
                    log.info("Region updated from " + region.getName() + " on " + name);
                    region.setName(name);
                    service.updateRegion(region);
                } else {
                    req.setAttribute(ActionConstants.REGION_ERROR, true);
                    return new ActionResult(ActionConstants.EDIT_REGIONS_PAGE);
                }
            }
        }
        return new ActionResult(ActionConstants.EDIT_REGIONS_PAGE, true);
    }
}
