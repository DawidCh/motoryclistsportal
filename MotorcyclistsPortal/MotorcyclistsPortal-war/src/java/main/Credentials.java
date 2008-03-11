/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Credentials implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
        String reqURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if (reqURI.split("/")[2].equals("logout.html")) {
            if (null != session) {
                session.invalidate();
            }
            return new ModelAndView("index");
        } else// if(reqURI.split("/")[2].equals("login.html"))
        {
            if (null == request.getUserPrincipal()) {
                String key = request.getParameter("error");
                if (null != key) {
                    LocaleProvider loc = (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
                    DefaultValues def = (DefaultValues) BeanGetter.getScopedBean("defaultValues", request);
                    return new ModelAndView("login", "error", loc.getMessage(key, null, def.getLocale()));
                }
                return new ModelAndView("login");
            } else {
                return new ModelAndView("redirect:/secured/main.html");
            }
        }
    }
}
