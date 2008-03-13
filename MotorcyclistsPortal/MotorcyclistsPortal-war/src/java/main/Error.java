/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.ApplicationSettings;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Error implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LocaleProvider loc = (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        formInfo.put("failColor", DefaultValues.getFailColor());
        formInfo.put("succColor", DefaultValues.getSuccColor());
        formInfo.put("pageTitle", loc.getMessage("error.pageTitle", null, defaultLocale));
        formInfo.put("languages",
                ((ApplicationSettings)BeanGetter.getScopedBean("settings", request)).getAvailableLanguages(request));
        return new ModelAndView("unsecured/error", formInfo);
    }
}
