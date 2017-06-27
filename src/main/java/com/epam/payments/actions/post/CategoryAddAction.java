package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Category;
import com.epam.payments.services.ManagementService;
import com.epam.payments.services.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code CategoryAddAction} allows admin add new category
 */
public class CategoryAddAction implements Action {
    private static final Logger log = Logger.getLogger(CategoryAddAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();

        String name = req.getParameter(ActionConstants.CATEGORY_NAME);

        Category category = new Category();

        if (name != null) {
            try {
                for (Category c : service.getListOfCategories()) {
                    if (!c.getName().equals(name)) {
                        category.setName(name);
                    } else {
                        req.setAttribute(ActionConstants.CATEGORY_ERROR, ActionConstants.TRUE);
                        return new ActionResult(ActionConstants.EDIT_CATEGORIES_PAGE);
                    }
                }
                service.createNewCategory(category);
            } catch (ServiceException e) {
                log.error("Cannot add new category ", e);
            }
        }
        log.info("Added new category " + name);
        return new ActionResult(ActionConstants.EDIT_CATEGORIES_PAGE, ActionConstants.isRedirect);
    }
}