/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.LocaleProvider;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class Error implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        formInfo.put("pageTitle", localeProvider.getMessage("error.pageTitle", null, defaultLocale));
        // </editor-fold>
        AuthenticationException aex = (AuthenticationException) request.getSession(false).getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
        try {
            MPLogger.severe(aex.getMessage());
            formInfo.put("errorMessage", aex.getMessage());

        } catch (NullPointerException nullPointerException) {
            String errorMessage = request.getParameter("errorMessage");
            if(null != errorMessage)
                formInfo.put("errorMessage", errorMessage);
            else
                formInfo.put("errorMessage", localeProvider.getMessage("error.otherError", null, defaultLocale));
        }

        return new ModelAndView("unsecured/error", formInfo);
    }
}
