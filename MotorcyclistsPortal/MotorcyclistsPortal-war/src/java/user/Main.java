/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import utils.ApplicationSettings;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Main implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
        UserSession userSession = (UserSession) BeanGetter.getScopedBean("userSession", request);
        LocaleProvider loc = (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
        userSession.setValues(request);
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("login", request.getUserPrincipal().getName());
        info.put("pageTitle", loc.getMessage("main.pageTitle", null, request.getLocale()));
        info.put("failColor", DefaultValues.getFailColor());
        info.put("succColor", DefaultValues.getSuccColor());
        info.put("languages",
                ((ApplicationSettings)BeanGetter.getScopedBean("settings", request)).getAvailableLanguages(request));
        return new ModelAndView("secured/main", info);
    }
}
