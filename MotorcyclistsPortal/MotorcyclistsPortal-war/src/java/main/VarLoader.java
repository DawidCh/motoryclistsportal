/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import user.UserSession;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class VarLoader extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        try {
            String login = request.getUserPrincipal().getName();
            if (null != request.getParameter("saveLocale") && null != login) {
                UserSession us = BeanGetter.getUserSession(request);
                us.setLanguageInDB(request, RequestContextUtils.getLocale(request));
                us.refreshUserFromDB();
            }
        } catch (NullPointerException npe) {
            MPLogger.severe("User is not in the session in VarLoader");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView map) throws Exception {
        map.addObject("failColor", DefaultValues.getFailColor());
        map.addObject("succColor", DefaultValues.getSuccColor());
        map.addObject("currentLocale", RequestContextUtils.getLocale(request).toString());

        try {
            map.addObject("login", request.getUserPrincipal().getName());
            map.addObject("favouriteLocale", BeanGetter.getUserSession(request).getFavouriteLoc().toString());
        } catch (NullPointerException nullPointerException) {
            MPLogger.severe("User is not in the session in VarLoader");
        }
        map.addObject("languages", BeanGetter.getApplicationSettings(request).getAvailableLanguages());
    }
}
