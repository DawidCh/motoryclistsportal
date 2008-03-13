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
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Credentials {

    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (null == request.getUserPrincipal()) {
            Locale defaultLocale = RequestContextUtils.getLocale(request);
            HashMap<String, Object> formInfo = new HashMap<String, Object>();
            LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
            String key = request.getParameter("error");
            if (null != key) {
                formInfo.put("message", localeProvider.getMessage(key, null, defaultLocale));
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("login", formInfo);
            }
            return new ModelAndView("login", formInfo);
        } else {
            return new ModelAndView("redirect:/secured/main.html");
        }
    }

    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        session.invalidate();
        return new ModelAndView("redirect:/index.html");
    }
}
