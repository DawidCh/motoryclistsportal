/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import security.DetailedUserInformation;
import utils.BeanGetter;
import utils.ApplicationSettings;
import utils.LocaleProvider;
import utils.MPException;

/**
 * Interceptor class used for setting available languages and storing selected
 * in database.
 * @author Dawid Chojnacki
 */
public class VarLoader extends HandlerInterceptorAdapter {

    /**
     * Method which is invoking before rendering page.
     * @param request current portlet render request
     * @param response current portlet render response
     * @param object chosen handler to execute, for type and/or instance
     * examination
     * @throws java.lang.Exception
     */
    @Override
    public boolean preHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object object)
            throws Exception {
        Logger.getLogger("E").trace("Entering to: preHandle");
        boolean result = true;
        if (null != request.getParameter("saveLocale")
                && SecurityContextHolder.getContext().getAuthentication().
                isAuthenticated()) {
            try {
                this.persistDLocales(request);
            } catch (MPException mPException) {
                response.
                        sendRedirect("error.html?errorMessage="
                        + mPException.getMessage());
                result = false;
            }
        }
        Logger.getLogger("E").trace("Exiting from: preHandle");
        return result;
    }

    /**
     * Method which is invoking after rendering page.
     * @param request current portlet render request
     * @param response current portlet render response
     * @param object chosen handler to execute, for type and/or instance
     * examination
     * @param map the ModelAndView that the handler returned (can also be null)
     * @throws java.lang.Exception
     */
    @Override
    public void postHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object object,
            final ModelAndView map) throws Exception {
        Logger.getLogger("E").trace("Entering to: postHandle");
        try {
            map.addObject("languages", BeanGetter.
                    getApplicationSettings(request).getAvailableLanguages());
            if (SecurityContextHolder.getContext().
                    getAuthentication().isAuthenticated()) {
                map.addObject("user", SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal());
            }
            map.addObject("currentLocale", RequestContextUtils.
                    getLocale(request).toString());
            map.addObject("failColor", ApplicationSettings.getFailColour());
            map.addObject("succColor", ApplicationSettings.getSuccColour());
        } catch (Exception ex) {
            Logger.getLogger("E").info("User is not in the session "
                    + "in " + this.getClass().getCanonicalName()
                    + " postHandle");
        }
        if (request.getContextPath().contains("logout")) {
            SecurityContextHolder.setContext(new SecurityContextImpl());
            request.getSession(false).invalidate();
        }
        Logger.getLogger("E").trace("Exiting from: postHandle");
    }

    /**
     * Method used for storing in db selected locale (language).
     * @param request HTTP request object
     * @throws utils.MPException
     */
    public void persistDLocales(final HttpServletRequest request)
            throws MPException {
        Logger.getLogger("E").trace("Entering to: persistDLocales");
        Locale localeToSet = RequestContextUtils.getLocale(request);
        try {
            DetailedUserInformation userInfo = BeanGetter.getUserInfo();
            userInfo.setLocale(localeToSet);
            BeanGetter.lookupUserFacade().edit(userInfo.getUser());
        } catch (Exception ex) {
            Logger.getLogger("E").
                    error("Error while persisting language at UserSession");
            LocaleProvider loc = BeanGetter.getLocaleProvider(request);
            throw new MPException(
                    loc.getMessage("session.errorWhilePersist",
                    null, localeToSet));
        }
        Logger.getLogger("E").trace("Exiting from: persistDLocales");
    }
}
