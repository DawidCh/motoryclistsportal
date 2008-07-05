/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Error implements Controller {

    /**
     * Method used for handling error page.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: handleRequest");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        formInfo.put("pageTitle", localeProvider.getMessage("error.pageTitle"
                , null, defaultLocale));
        // </editor-fold>
        AuthenticationException aex = (AuthenticationException)
                request.getSession(false).
                getAttribute(AbstractProcessingFilter.
                SPRING_SECURITY_LAST_EXCEPTION_KEY);
        try {
            Logger.getLogger("E").error(aex.getMessage());
            formInfo.put("errorMessage", aex.getMessage());
        } catch (NullPointerException nullPointerException) {
            String errorMessage = request.getParameter("errorMessage");
            if (null != errorMessage) {
                formInfo.put("errorMessage", errorMessage);
            } else {
                formInfo.put("errorMessage",
                        localeProvider.getMessage("error.otherError",
                        null, defaultLocale));
            }
        }
        Logger.getLogger("E").trace("Exiting from: handleRequest");
        return new ModelAndView("unsecured/error", formInfo);
    }
}
