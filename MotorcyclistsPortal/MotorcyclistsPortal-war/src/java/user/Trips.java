/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import security.DetailedUserInformation;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Trips {
    
    public ModelAndView showList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        DetailedUserInformation userInfo = BeanGetter.getUserInfo();
        List trips;
        //List<Motorcycle> trips = new ArrayList<Motorcycle>(
          //      BeanGetter.lookupMotorcycleFacade().findByLogin(userInfo.getUsername()));
        //formInfo.put("trips", trips);
       formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        return new ModelAndView("trips/list");
    }
    
    public ModelAndView addTrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.getMessage("trips.formTitle.add", null, defaultLocale));
        String form = request.getParameter("form");

        if (form != null) {
            formInfo.put("action", "edit.html");
            return new ModelAndView("trips/add");
        }
        formInfo.put("action", "new.html");
        return new ModelAndView("trips/add");
    }
    
    public ModelAndView editTrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("formTitle", localeProvider.getMessage("trips.formTitle.edit", null, defaultLocale));
        String form = request.getParameter("form");
        
        String message;
        if (form != null) {
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
        }
        return new ModelAndView("trips/add");
    }

    public ModelAndView deleteTrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        
        return new ModelAndView("/trips/list");
    }

    public ModelAndView details(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));

        return new ModelAndView("trips/details");
    }
}
