package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.services.ManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code RegionSettingsPageAction} returns admin on edit-regions.jsp
 * where admin can add or edit regions
 */
public class RegionSettingsPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();
        req.setAttribute(ActionConstants.LIST_OF_REGIONS, service.getListOfRegions());
        return new ActionResult(ActionConstants.EDIT_REGIONS_PAGE);
    }
}
