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
import utils.BeanGetter;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class IndexController implements Controller{

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale currentLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        formInfo.put("pageTitle", localeProvider.getMessage("index.pageTitle", null, currentLocale));
        // </editor-fold>
        return new ModelAndView("index", formInfo);
    }

}
