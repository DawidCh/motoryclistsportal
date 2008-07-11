/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation.controllers;

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
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 * Class used for handle login page.
 * @author Dawid Chojnacki
 */
public class Credentials implements Controller {

    /**
     * Method used for handle login page requests.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: handleRequest");
        ModelAndView result = null;
        if (null == request.getUserPrincipal()) {
            Locale defaultLocale = RequestContextUtils.getLocale(request);
            HashMap < String, Object > formInfo =
                    new HashMap < String, Object >();
            LocaleProvider localeProvider =
                    BeanGetter.getLocaleProvider(request);
            formInfo.put("pageTitle", localeProvider.
                    getMessage("loginpage.pageTitle", null, defaultLocale));
            String key = request.getParameter("error");
            if (null != key) {
                formInfo.put("message", localeProvider.getMessage(key,
                        null, defaultLocale));
                formInfo.put("messColor", DefaultValues.getFailColour());
                AuthenticationException aex = (AuthenticationException) request.
                        getSession(false).getAttribute(AbstractProcessingFilter.
                        SPRING_SECURITY_LAST_EXCEPTION_KEY);
                Logger.getLogger("E").error(aex.getMessage());
            }
            result = new ModelAndView("login", formInfo);
        } else {
            result = new ModelAndView("redirect:/secured/main.html");
        }
        Logger.getLogger("E").trace("Exiting from: handleRequest");
        return result;
    }
}
