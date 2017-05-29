package com.epam.payments.actions.get;

import com.epam.payments.actions.api.Action;
import com.epam.payments.actions.api.ActionConstants;
import com.epam.payments.actions.api.ActionResult;
import com.epam.payments.models.Provider;
import com.epam.payments.services.ProviderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * {@code ProvidersLogotypeAction} allows to display providers logotype.
 */
public class ProvidersLogotypeAction implements Action {
    private static final Logger log = Logger.getLogger(ProvidersLogotypeAction.class);

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    private static final int OFFSET = 0;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ProviderService providerService = new ProviderService();

        int imageId = Integer.parseInt(req.getParameter(ActionConstants.LOGO));

        resp.setContentType(ActionConstants.CONTENT_TYPE_IMAGE);

        int length;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

        Provider provider = providerService.findProviderById(imageId);
        try (OutputStream outputStream = resp.getOutputStream()) {
            while ((length = provider.getLogotype().read(buffer)) > OFFSET) {
                outputStream.write(buffer, OFFSET, length);
            }
        } catch (IOException e) {
            log.error("Cannot display providers logotypes ", e);
        }
        return new ActionResult(ActionConstants.LOGO);
    }
}