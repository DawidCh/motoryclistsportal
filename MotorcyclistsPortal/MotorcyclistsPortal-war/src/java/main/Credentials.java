/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Locale;
import utils.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Credentials implements Controller{

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (null == request.getUserPrincipal()) {
            Locale defaultLocale = RequestContextUtils.getLocale(request);
            HashMap<String, Object> formInfo = new HashMap<String, Object>();
            LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
            formInfo.put("pageTitle", localeProvider.getMessage("loginpage.pageTitle", null, defaultLocale));
            String key = request.getParameter("error");
            if (null != key) {
                formInfo.put("message", localeProvider.getMessage(key, null, defaultLocale));
                formInfo.put("messColor", DefaultValues.getFailColor());
                AuthenticationException aex = (AuthenticationException) request.
                        getSession(false).getAttribute(AbstractProcessingFilter.
                        ACEGI_SECURITY_LAST_EXCEPTION_KEY);
                MPLogger.severe(aex.getMessage());
                return new ModelAndView("login", formInfo);
            }
            return new ModelAndView("login", formInfo);
        } else {
            return new ModelAndView("redirect:/secured/main.html");
        }
    }
}
