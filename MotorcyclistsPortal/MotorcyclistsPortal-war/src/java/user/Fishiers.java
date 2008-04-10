/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.Fishier;
import entities.Motorcycle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class Fishiers {

    public ModelAndView showList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        List<Fishier> fishiers = findFishiers();
        formInfo.put("pageTitle", localeProvider.getMessage("fishiers.pageTitle", null, defaultLocale));
        formInfo.put("fishiers", fishiers);
        return new ModelAndView("fishiers/list", formInfo);
    }

    public ModelAndView addFishier(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("fishiers.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.getMessage("fishiers.formTitle.add", null, defaultLocale));
        String form = request.getParameter("form");
        String message;

        if (form != null) {
            String description = request.getParameter("description");
            if (description == null || description.length() == 0) {
                MPLogger.severe("Not all fields filled in Fishiers.add");
                message = localeProvider.getMessage("notAllFilled", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                return new ModelAndView("fishiers/add", formInfo);
            }
            Fishier newFishier = new Fishier();
            newFishier.setDescription(description);

            try {
                BeanGetter.lookupFishierFacade().create(newFishier);
            } catch (Exception exception) {
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                MPLogger.severe("Error wihle persisting in db at Fishiers.add: " + exception.getMessage());
                return new ModelAndView("fishier/add", formInfo);
            }
            formInfo.put("fishier", newFishier);
            formInfo.put("action", "edit.html");
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
            formInfo.put("formTitle", localeProvider.getMessage("fishiers.formTitle.edit", null, defaultLocale));
            return new ModelAndView("fishiers/add", formInfo);
        }
        formInfo.put("action", "new.html");
        return new ModelAndView("fishiers/add");
    }

    public ModelAndView editFishier(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("formTitle", localeProvider.getMessage("fishiers.formTitle.edit", null, defaultLocale));
        formInfo.put("action", "edit.html");
        String form = request.getParameter("form");
        String fishierId = request.getParameter("fishier");
        Fishier fishier;
        String message;

        try {
            fishier = BeanGetter.lookupFishierFacade().find(new Integer(fishierId));
            formInfo.put("fishier", fishier);
        } catch (Exception ex) {
            MPLogger.severe("Fishier not found at fishiers edit: " + fishierId);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("message", localeProvider.getMessage("fishiers.fishierNotFound", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            ex.printStackTrace();
            return new ModelAndView(this.showList(request, response).getView(), map);
        }

        if (form != null) {
            String description = request.getParameter("description");
            if (description == null || description.length() == 0) {
                MPLogger.severe("Not all fields filled in Fishiers.edit");
                message = localeProvider.getMessage("notAllFilled", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("fishiers/add", formInfo);
            }

            try {
                if (!description.equals(fishier.getDescription())) {
                    fishier.setDescription(description);
                    BeanGetter.lookupFishierFacade().edit(fishier);
                }
            } catch (Exception exception) {
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                MPLogger.severe("Error wihle persisting in db at Fishiers.edit: " + exception.getMessage());
                exception.printStackTrace();
                return new ModelAndView("fishier/add", formInfo);
            }
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());

            return new ModelAndView("fishiers/add", formInfo);
        }
        return new ModelAndView("fishiers/add", formInfo);
    }

    public ModelAndView deleteFishier(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("fishiers.pageTitle", null, defaultLocale));
        String fishierId = request.getParameter("fishier");
        String bikeId = request.getParameter("bike");

        Fishier fishier;
        String message;

        try {
            MPLogger.severe("Poprawić zeby wyszukiwało tylko użytkownika fiszki.");
            fishier = BeanGetter.lookupFishierFacade().find(new Integer(fishierId));
        } catch (Exception ex) {
            MPLogger.severe("Fishier not found at fishiers edit: ");
            formInfo.put("message", localeProvider.getMessage("fishiers.fishierNotFound", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            ex.printStackTrace();
            if (bikeId != null) {
                formInfo.put("bike", bikeId);
                return new ModelAndView("redirect:/bikes/details.html", formInfo);
            } else {
                formInfo.put("fishiers", this.findFishiers());
                return new ModelAndView(this.details(request, response).getView(), formInfo);
            }
        }
        try {
            List<Motorcycle> bikes = BeanGetter.lookupMotorcycleFacade().findByFishier(fishier);
            for (Iterator<Motorcycle> it = bikes.iterator(); it.hasNext();) {
                Motorcycle motorcycle = it.next();
                motorcycle.setFishier(null);
                BeanGetter.lookupMotorcycleFacade().edit(motorcycle);
            }
            BeanGetter.lookupFishierFacade().remove(fishier);
        } catch (Exception exception) {
            message = localeProvider.getMessage("error.errorWhileDeleting", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getFailColor());
            MPLogger.severe("Error wihle deleting in db at Fishiers.delete: " + exception.getMessage());
            if (bikeId != null) {
                formInfo.put("bike", bikeId);
                return new ModelAndView("redirect:/bikes/details.html", formInfo);
            } else {
                formInfo.put("fishiers", this.findFishiers());
                return new ModelAndView("fishiers/list", formInfo);
            }
        }
        formInfo.put("message", localeProvider.getMessage("success", null, defaultLocale));
        formInfo.put("messColor", DefaultValues.getSuccColor());
        if (bikeId != null) {
            formInfo.put("bike", bikeId);
            return new ModelAndView("redirect:/bikes/details.html", formInfo);
        } else {
            formInfo.put("fishiers", this.findFishiers());
            return new ModelAndView("fishiers/list", formInfo);
        }
    }

    public ModelAndView details(
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("fishiers.pageTitle", null, defaultLocale));
        String fishierId = request.getParameter("fishier");
        Fishier fishier;

        try {
            fishier = BeanGetter.lookupFishierFacade().find(new Integer(fishierId));
            System.out.println(fishier.getDescription());
        } catch (Exception ex) {
            MPLogger.severe("Fishier not found at fishiers edit: " + fishierId);
            ex.printStackTrace();
            formInfo.put("message", localeProvider.getMessage("fishiers.fishierNotFound", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            return new ModelAndView(this.showList(request, response).getView(), formInfo);
        }

        formInfo.put("fishier", fishier);
        return new ModelAndView("fishiers/details", formInfo);
    }

    private List<Fishier> findFishiers() {
        List<Fishier> fishiers = BeanGetter.lookupFishierFacade().findAll();
        return fishiers;
    }
}
