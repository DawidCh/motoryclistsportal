/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.Action;
import entities.ActivityPeriod;
import entities.Fishier;
import entities.FishierElementBridge;
import entities.FishiersElement;
import entities.Motorcycle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import utils.MPException;
import utils.MPLogger;
import utils.MPUtilities;

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
        Motorcycle bike = null;
        String form = request.getParameter("form");
        String bikeId = request.getParameter("bike");
        try {
            if (bikeId == null) {
                throw new MPException("");
            }
            bike = MPUtilities.findBike(bikeId);
            formInfo.put("bike", bike);
        } catch (MPException mPException) {
            form = null;
            MPLogger.severe("Bike not found in Fishiers.add");
        }
        List<Motorcycle> bikes = this.getBikes();
        formInfo.put("bikes", bikes);
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
            newFishier.setLogin(BeanGetter.getUserInfo().getUser());
            newFishier.setMotorcycle(bike);
            bike.setFishier(newFishier);
            try {
                BeanGetter.lookupFishierFacade().create(newFishier);
                BeanGetter.lookupMotorcycleFacade().edit(bike);
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
        return new ModelAndView("fishiers/add", formInfo);
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
            fishier = this.findFishier(fishierId);
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
            fishier = this.findFishier(fishierId);
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

    public ModelAndView deleteElement(
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        String elementId = request.getParameter("element");
        String fishierId = request.getParameter("fishier");
        FishierElementBridge elementToDel = this.findFishierElementBridge(elementId);
        try {
            if (elementToDel == null) {
                throw new Exception();
            }
            BeanGetter.lookupFishierElementBridgeFacade().remove(elementToDel);
            //formInfo.put("elements", this.findElements(fishierId));
            formInfo.put("message", localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColor());
            //formInfo.put("fishierElementBridges", this.findFishierElementBridges());
        } catch (Exception exception) {
            MPLogger.severe("Error while deleting fishier element at Fishiers.deleteElement: " + elementId);
            exception.printStackTrace();
            formInfo.put("message", localeProvider.getMessage("error.errorWhileDeleting", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        }
        formInfo = (HashMap<String, Object>) this.details(request, response).getModel();
        return new ModelAndView("fishiers/details", formInfo);
    }

    public ModelAndView addElement(
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>

        formInfo = (HashMap<String, Object>) this.details(request, response).getModel();
        formInfo.put("elements", null);
        formInfo.put("fishierElementBridges", null);
        Fishier fishier = (Fishier) formInfo.get("fishier");
        String fishierElementBridge = request.getParameter("fishierElementBridge");
        String periodLength = request.getParameter("periodLength");
        String changeMileage = request.getParameter("changeMileage");
        String changeDate = request.getParameter("changeDate");
        String activityPeriod = request.getParameter("activityPeriod");
        String action = request.getParameter("action");

        try {
            FishiersElement fishEl = BeanGetter.lookupFishiersElementFacade().find(new Integer(fishierElementBridge));
            if (fishEl == null) {
                MPLogger.severe("Error while finding fishierelement in Fishiers.addElement");
                throw new Exception();
            }
            if (fishier == null) {
                MPLogger.severe("Error while finding fishier in Fishiers.addElement");
                throw new Exception();
            }

            FishierElementBridge element = new FishierElementBridge();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = sdf.parse(changeDate);

            element.setFishier(fishier);
            element.setFishierelement(fishEl);
            element.setPeriodlength(Integer.parseInt(periodLength));
            element.setActivityperiod(activityPeriod);
            element.setAction(action);
            element.setChangemileage(Integer.parseInt(changeMileage));
            element.setChangedate(newDate);

            BeanGetter.lookupFishierElementBridgeFacade().create(element);

            formInfo.put("elements", this.findElements(fishier.getId().toString()));
            formInfo.put("message", localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColor());
            formInfo.put("fishierElementBridges", this.findFishierElementBridges());
        } catch (NumberFormatException nfe) {
            formInfo.put("periodLength", null);
            formInfo.put("changeMileage", null);
            formInfo.put("changeDate", changeDate);
            formInfo.put("message", localeProvider.getMessage("error.parsingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            formInfo.put("periodLength", periodLength);
        } catch (ParseException ex) {
            formInfo.put("date", null);
            formInfo.put("message", localeProvider.getMessage("error.wrongDate", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } catch (Exception exception) {
            MPLogger.severe("Error while adding fishier element at Fishiers.addElement");
            exception.printStackTrace();
            formInfo.put("message", localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            formInfo.put("periodLength", periodLength);
        }
        return new ModelAndView("fishiers/details", formInfo);
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
        List<ActivityPeriod> periods = BeanGetter.lookupActivityPeriodFacade().findAll();
        List<FishiersElement> elements;
        List<Action> actions = BeanGetter.lookupActionFacade().findAll();
        List<FishierElementBridge> fishierElementBridges;

        formInfo.put("periods", periods);
        formInfo.put("actions", actions);

        try {
            elements = this.findElements(fishierId);
            formInfo.put("elements", elements);
            fishier = this.findFishier(fishierId);
            fishierElementBridges = this.findFishierElementBridgeByFishier(fishierId);
        } catch (Exception ex) {
            MPLogger.severe("Fishier not found at fishiers edit: " + fishierId);
            ex.printStackTrace();
            formInfo.put("message", localeProvider.getMessage("fishiers.fishierNotFound", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            return new ModelAndView(this.showList(request, response).getView(), formInfo);
        }
        formInfo.put("fishierElementBridges", fishierElementBridges);
        formInfo.put("fishier", fishier);
        return new ModelAndView("fishiers/details", formInfo);
    }

    public ModelAndView editElement(
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>

        formInfo = (HashMap<String, Object>) this.details(request, response).getModel();
        Fishier fishier = (Fishier) formInfo.get("fishier");
        String fishierElementBridgeId = request.getParameter("fishierElementBridge");
        String changeMileage = request.getParameter("changeMileage");
        String changeDate = request.getParameter("changeDate");

        try {
            FishierElementBridge fishEl = BeanGetter.lookupFishierElementBridgeFacade().find(new Integer(fishierElementBridgeId));
            if (fishEl == null) {
                MPLogger.severe("Error while finding fishierelementbridge in Fishiers.editElement");
                throw new Exception();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = sdf.parse(changeDate);

            if (!fishEl.getChangedate().equals(changeDate)) {
                fishEl.setChangedate(newDate);
            }
            if (fishEl.getChangemileage() != Integer.parseInt(changeMileage)) {
                fishEl.setChangemileage(Integer.parseInt(changeMileage));
            }

            BeanGetter.lookupFishierElementBridgeFacade().edit(fishEl);

            formInfo.put("elements", this.findElements(fishier.getId().toString()));
            formInfo.put("message", localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColor());
        } catch (NumberFormatException nfe) {
            formInfo.put("message", localeProvider.getMessage("error.parsingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } catch (ParseException ex) {
            formInfo.put("message", localeProvider.getMessage("error.wrongDate", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } catch (Exception exception) {
            MPLogger.severe("Error while editing fishier element at Fishiers.editElement");
            exception.printStackTrace();
            formInfo.put("message", localeProvider.getMessage("error.errorWhileEditing", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } finally {formInfo.put("fishierElementBridges", this.findFishierElementBridges());}
        return new ModelAndView("fishiers/details", formInfo);
    }

    private Fishier findFishier(String fishierId) throws MPException {
        for (Fishier fishier : BeanGetter.getUserInfo().getFishiers()) {
            if (fishier.getId().toString().equals(fishierId)) {
                return fishier;
            }
        }
        throw new MPException("Fishier not found at Fishier.findFishier");
    }

    private List<Fishier> findFishiers() {
        return BeanGetter.getUserInfo().getFishiers();
    }

    private FishierElementBridge findFishierElementBridge(String elementId) {
        return BeanGetter.lookupFishierElementBridgeFacade().find(Integer.parseInt(elementId));
    }

    private List<FishiersElement> findElements(String fishierId) {
        return BeanGetter.lookupFishiersElementFacade().findAllNotConnWithFishier(fishierId);
    }

    private List<FishierElementBridge> findFishierElementBridges() {
        return BeanGetter.lookupFishierElementBridgeFacade().findAll();
    }

    private List<FishierElementBridge> findFishierElementBridgeByFishier(String fishierId) {
        return BeanGetter.lookupFishierElementBridgeFacade().findAllByFishier(fishierId);
    }

    private List<Motorcycle> getBikes() {
        return BeanGetter.lookupMotorcycleFacade().findWithoutFishier();
    }
}
