/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import security.DetailedUserInformation;
import entities.Motorcycle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class Bikes {

    public ModelAndView showList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        DetailedUserInformation userInfo = BeanGetter.getUserInfo();
        List<Motorcycle> bikes = new ArrayList<Motorcycle>(BeanGetter.lookupMotorcycleFacade().findByLogin(userInfo.getUser()));
        formInfo.put("bikes", bikes);
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        return new ModelAndView("bikes/list", formInfo);
    }

    public ModelAndView addBike(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        return new ModelAndView("bikes/add", formInfo);
    }

    public ModelAndView deleteBike(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        if (bikeId == null) {
            MPLogger.severe("Null bike id");
            formInfo.put("errorMessage", localeProvider.getMessage("error.otherError", null, defaultLocale));
            return new ModelAndView("unsecured/error", formInfo);
        }
        DetailedUserInformation user = BeanGetter.getUserInfo();
        Map map = null;
        Motorcycle bikeToDel = null;
        try {
            bikeToDel = this.findBike(bikeId);
            BeanGetter.lookupMotorcycleFacade().remove(bikeToDel);
            user.refreshMotorcycleCollection();
            map = this.showList(request, response).getModel();
        } catch (Exception mPException) {
            map = this.showList(request, response).getModel();
            map.put("message", localeProvider.getMessage("error.errorWhileDeleting", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            return new ModelAndView("/bikes/list", map);
        }
        map.put("message", localeProvider.getMessage("success", null, defaultLocale));
        map.put("messColor", DefaultValues.getSuccColor());
        return new ModelAndView("/bikes/list", map);
    }

    public ModelAndView details(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        Motorcycle bike = null;
        try {
            bike = this.findBike(bikeId);
            if (bike == null) {
                throw new Exception("Bike not found");
            }
            formInfo.put("bike", bike);
        } catch (Exception ex) {
            MPLogger.severe("Bike not found");
            formInfo.put("errorMessage", localeProvider.getMessage("bikes.bikeNotFound", null, defaultLocale));
            return new ModelAndView("unsecured/error", formInfo);
        }
        return new ModelAndView("bikes/details", formInfo);
    }

    private Motorcycle findBike(String bikeId) throws MPException {
        DetailedUserInformation user = BeanGetter.getUserInfo();
        for (Motorcycle bike : BeanGetter.getUserInfo().getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                return bike;
            }
        }
        throw new MPException("Bike not found");
    }
}
