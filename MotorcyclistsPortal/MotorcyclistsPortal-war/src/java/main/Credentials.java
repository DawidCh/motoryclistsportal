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
public class Credentials implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reqURI = request.getRequestURI();
        HttpSession session = request.getSession();
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        LocaleProvider loc = (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        formInfo.put("failColor", DefaultValues.getFailColor());
        formInfo.put("succColor", DefaultValues.getSuccColor());
        formInfo.put("pageTitle", loc.getMessage("loginpage.pageTitle", null, defaultLocale));
        formInfo.put("languages",
                ((ApplicationSettings)BeanGetter.getScopedBean("settings", request)).getAvailableLanguages(request));
        if (reqURI.split("/")[2].equals("logout.html")) {
            if (null != session) {
                session.invalidate();
            }
            return new ModelAndView("redirect:/index.html");
        } else// if(reqURI.split("/")[2].equals("login.html"))
        {
            if (null == request.getUserPrincipal()) {
                String key = request.getParameter("error");
                if (null != key) {
                    formInfo.put("message", loc.getMessage(key, null, defaultLocale));
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    return new ModelAndView("login", formInfo);
                }
                return new ModelAndView("login", formInfo);
            } else {
                return new ModelAndView("redirect:/secured/main.html");
            }
        }
    }
}
