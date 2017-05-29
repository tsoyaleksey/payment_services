package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.services.ManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code CategorySettingsPageAction} returns user on edit-categories page.
 */
public class CategorySettingsPageAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();
        req.setAttribute(ActionConstants.LIST_OF_CATEGORIES, service.getListOfCategories());
        return new ActionResult(ActionConstants.EDIT_CATEGORIES_PAGE);
    }
}
