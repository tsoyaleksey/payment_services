package com.epam.payments.actions.post;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Category;
import com.epam.payments.services.ManagementService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code CategoryEditAction} allows admin to edit categories
 */
public class CategoryEditAction implements Action {
    private static final Logger log = Logger.getLogger(CategoryEditAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService service = new ManagementService();

        String name = req.getParameter(ActionConstants.CATEGORY_NAME);
        int id      = Integer.parseInt(req.getParameter(ActionConstants.CATEGORY_ID));

        if (name != null) {
            for (Category c : service.getListOfCategories()) {
                if (!c.getName().equals(name)) {
                    Category category = service.findCategoryById(id);
                    category.setName(name);
                    service.updateCategory(category);
                    log.info("Category updated from " + category.getName() + " on " + name);
                } else {
                    req.setAttribute(ActionConstants.CATEGORY_ERROR, true);
                    return new ActionResult(ActionConstants.EDIT_CATEGORIES_PAGE);
                }
            }
        }
        return new ActionResult(ActionConstants.EDIT_CATEGORIES_PAGE, true);
    }
}