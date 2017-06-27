package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Region;
import com.epam.payments.models.User;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.UserService;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code RegionSelectAction} allows user to select a region.
 */
public class RegionSelectAction implements Action {
    private static final Logger log = Logger.getLogger(RegionSelectAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService     = new UserService();
        ManagementService service   = new ManagementService();

        int id          = Integer.parseInt(req.getParameter(ActionConstants.USER_REGION));
        String number   = String.valueOf(req.getSession().getAttribute(ActionConstants.USER_NUMBER));

        User user       = userService.findUserByNumber(number);
        Region region   = service.findRegionById(id);
        user.setRegion(region);

        try {
            userService.setNewRegionForUser(user);
        } catch (ServiceException e) {
            log.error("Cannot set new region for user ", e);
        }
        req.getSession().setAttribute(ActionConstants.REGION_ID, region.getId());
        req.getSession().setAttribute(ActionConstants.USER_REGION, region.getName());

        return new ActionResult(req.getHeader(ActionConstants.REFERER), ActionConstants.isRedirect);
    }
}