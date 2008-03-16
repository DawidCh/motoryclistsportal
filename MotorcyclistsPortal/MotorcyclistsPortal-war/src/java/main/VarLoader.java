/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import user.DetailedUserInformation;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class VarLoader extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if (null != request.getParameter("saveLocale") &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                this.persistDLocales(request);
            } catch (MPException mPException) {
                response.sendRedirect("error.html?errorMessage=" + mPException.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView map) throws Exception {
        try {
            map.addObject("languages", BeanGetter.getApplicationSettings(request).getAvailableLanguages());
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                map.addObject("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
            map.addObject("currentLocale", RequestContextUtils.getLocale(request).toString());
            map.addObject("failColor", DefaultValues.getFailColor());
            map.addObject("succColor", DefaultValues.getSuccColor());
        } catch (Exception ex) {
            MPLogger.severe("User is not in the session in VarLoader.postHandle");
        }
    }

    public void persistDLocales(HttpServletRequest request) throws MPException {
        Locale localeToSet = RequestContextUtils.getLocale(request);
        try {
            DetailedUserInformation userInfo = BeanGetter.getUserInfo();
            userInfo.setLocale(localeToSet);
            BeanGetter.lookupUserBean().editUser(userInfo.getUser());
        } catch (MPException ex) {
            MPLogger.severe("Error while persisting language at UserSession because user is not in the session");
            LocaleProvider loc = BeanGetter.getLocaleProvider(request);
            throw new MPException(loc.getMessage("session.errorWhilePersist", null, localeToSet));
        } catch (Exception ex) {
            MPLogger.severe("Error while persisting language at UserSession because of other error");
        }
    }
}