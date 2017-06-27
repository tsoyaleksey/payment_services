package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * {@code SetLocaleAction} is necessary to selecting locale on the website
 * through using Resource bundle.
 */
public class SetLocaleAction implements Action {
    private static final Logger log = Logger.getLogger(SetLocaleAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String lang = req.getParameter(ActionConstants.LANG);
        Config.set(req.getSession(), Config.FMT_LOCALE, new Locale(lang));
        req.getSession().setAttribute(ActionConstants.LANG, lang);
        log.info("The selected language is " + lang);
        return new ActionResult(req.getHeader(ActionConstants.REFERER), ActionConstants.isRedirect);
    }
}
