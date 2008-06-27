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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Method used for showing list of fishiers which are owned by logged user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView showList(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        List < Fishier > fishiers = findFishiers();
        formInfo.put("pageTitle", localeProvider.getMessage(
                "fishiers.pageTitle", null, defaultLocale));
        formInfo.put("fishiers", fishiers);
        return new ModelAndView("fishiers/list", formInfo);
    }

    /**
     * Method used for adding new fishier for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView addFishier(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage(
                "fishiers.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.getMessage(
                "fishiers.formTitle.add", null, defaultLocale));
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
            MPLogger.error("Bike not found in Fishiers.add");
        }
        List < Motorcycle > bikes = this.getBikes();
        formInfo.put("bikes", bikes);
        String message;

        if (form != null) {
            String description = request.getParameter("description");
            if (description == null || description.length() == 0) {
                MPLogger.error("Not all fields filled in Fishiers.add");
                message = localeProvider.getMessage(
                        "notAllFilled", null, defaultLocale);
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
                message = localeProvider.getMessage(
                        "error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                MPLogger.error("Error wihle persisting in db at Fishiers.add: "
                        + exception.getMessage());
                return new ModelAndView("fishier/add", formInfo);
            }
            formInfo.put("fishier", newFishier);
            formInfo.put("action", "edit.html");
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
            formInfo.put("formTitle", localeProvider.getMessage(
                    "fishiers.formTitle.edit", null, defaultLocale));
            return new ModelAndView("fishiers/add", formInfo);
        }
        formInfo.put("action", "new.html");
        return new ModelAndView("fishiers/add", formInfo);
    }

    /**
     * Method used for editing fishier for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView editFishier(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("formTitle", localeProvider.getMessage(
                "fishiers.formTitle.edit", null, defaultLocale));
        formInfo.put("action", "edit.html");
        String form = request.getParameter("form");
        String fishierId = request.getParameter("fishier");
        Fishier fishier;
        String message;

        List < Motorcycle > bikes = this.getBikes();
        formInfo.put("bikes", bikes);

        try {
            fishier = this.findFishier(fishierId);
            formInfo.put("fishier", fishier);
        } catch (Exception ex) {
            MPLogger.error("Fishier not found at fishiers edit: " + fishierId);
            HashMap < String, Object > map = new HashMap < String, Object >();
            map.put("message", localeProvider.getMessage(
                    "fishiers.fishierNotFound", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            ex.printStackTrace();
            return new ModelAndView(this.showList(request, response).getView(),
                    map);
        }

        if (form != null) {
            String description = request.getParameter("description");
            if (description == null || description.length() == 0) {
                MPLogger.error("Not all fields filled in Fishiers.edit");
                message = localeProvider.getMessage("notAllFilled", null,
                        defaultLocale);
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
                message = localeProvider.getMessage("error.errorWhileAdding",
                        null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                MPLogger.error("Error wihle persisting"
                        + "in db at Fishiers.edit: " + exception.getMessage());
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

    /**
     * Method used for deleting fishier for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView deleteFishier(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage(
                "fishiers.pageTitle", null, defaultLocale));
        String fishierId = request.getParameter("fishier");
        String bikeId = request.getParameter("bike");

        Fishier fishier;
        String message;

        try {
            fishier = this.findFishier(fishierId);
        } catch (Exception ex) {
            MPLogger.error("Fishier not found at fishiers edit: ");
            formInfo.put("message", localeProvider.getMessage(
                    "fishiers.fishierNotFound", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            ex.printStackTrace();
            if (bikeId != null) {
                formInfo.put("bike", bikeId);
                return new ModelAndView(
                        "redirect:/bikes/details.html", formInfo);
            } else {
                formInfo.put("fishiers", this.findFishiers());
                return new ModelAndView(
                        this.details(request, response).getView(), formInfo);
            }
        }
        try {
            List < Motorcycle > bikes =
                    BeanGetter.lookupMotorcycleFacade().findByFishier(fishier);
            for (Iterator < Motorcycle > it = bikes.iterator(); it.hasNext();) {
                Motorcycle motorcycle = it.next();
                motorcycle.setFishier(null);
                BeanGetter.lookupMotorcycleFacade().edit(motorcycle);
            }
            BeanGetter.lookupFishierFacade().remove(fishier);
        } catch (Exception exception) {
            message = localeProvider.getMessage(
                    "error.errorWhileDeleting", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getFailColor());
            MPLogger.error("Error wihle deleting in db at Fishiers.delete: "
                    + exception.getMessage());
            if (bikeId != null) {
                formInfo.put("bike", bikeId);
                return new ModelAndView(
                        "redirect:/bikes/details.html", formInfo);
            } else {
                formInfo.put("fishiers", this.findFishiers());
                return new ModelAndView("fishiers/list", formInfo);
            }
        }
        formInfo.put("message", localeProvider.getMessage(
                "success", null, defaultLocale));
        formInfo.put("messColor", DefaultValues.getSuccColor());
        if (bikeId != null) {
            formInfo.put("bike", bikeId);
            return new ModelAndView("redirect:/bikes/details.html", formInfo);
        } else {
            formInfo.put("fishiers", this.findFishiers());
            return new ModelAndView("fishiers/list", formInfo);
        }
    }

    /**
     * Method used for deleting fishier element for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView deleteElement(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        String elementId = request.getParameter("element");
        FishierElementBridge elementToDel =
                this.findFishierElementBridge(elementId);
        try {
            if (elementToDel == null) {
                throw new Exception();
            }
            BeanGetter.lookupFishierElementBridgeFacade().remove(elementToDel);
            formInfo.put("message", localeProvider.getMessage(
                    "success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColor());
        } catch (Exception exception) {
            MPLogger.error("Error while deleting fishier element at"
                    + "Fishiers.deleteElement: " + elementId);
            exception.printStackTrace();
            formInfo.put(
                    "message", localeProvider.getMessage(
                    "error.errorWhileDeleting", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        }
        formInfo = (HashMap<String, Object>) this.details(
                request, response).getModel();
        return new ModelAndView("fishiers/details", formInfo);
    }

    /**
     * Method used for adding fishier element for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView addElement(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>

        formInfo = ( HashMap < String, Object > )
                this.details(request, response).getModel();
        formInfo.put("elements", null);
        formInfo.put("fishierElementBridges", null);
        Fishier fishier = (Fishier) formInfo.get("fishier");
        String fishierElementBridge =
                request.getParameter("fishierElementBridge");
        String periodLength = request.getParameter("periodLength");
        String changeMileage = request.getParameter("changeMileage");
        String changeDate = request.getParameter("changeDate");
        String activityPeriod = request.getParameter("activityPeriod");
        String action = request.getParameter("action");

        try {
            FishiersElement fishEl = BeanGetter.lookupFishiersElementFacade().
                    find(new Integer(fishierElementBridge));
            if (fishEl == null) {
                MPLogger.error(
                        "Error while finding"
                        + "fishierelement in Fishiers.addElement");
                throw new Exception();
            }
            if (fishier == null) {
                MPLogger.error(
                        "Error while finding fishier in Fishiers.addElement");
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

            formInfo.put("elements",
                    this.findElements(fishier.getId().toString()));
            formInfo.put("message",
                    localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor",
                    DefaultValues.getSuccColor());
            formInfo.put("fishierElementBridges",
                    this.findFishierElementBridges());
        } catch (NumberFormatException nfe) {
            formInfo.put("periodLength", null);
            formInfo.put("changeMileage", null);
            formInfo.put("changeDate", changeDate);
            formInfo.put("message",
                    localeProvider.getMessage(
                    "error.parsingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            formInfo.put("periodLength", periodLength);
        } catch (ParseException ex) {
            formInfo.put("date", null);
            formInfo.put("message",
                    localeProvider.getMessage(
                    "error.wrongDate", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } catch (Exception exception) {
            MPLogger.error("Error while adding fishier"
                    + "element at Fishiers.addElement");
            exception.printStackTrace();
            formInfo.put("message", localeProvider.
                    getMessage("error.errorWhileAdding", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            formInfo.put("periodLength", periodLength);
        }
        return new ModelAndView("fishiers/details", formInfo);
    }

    /**
     * Method used for display fishier for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView details(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("fishiers.pageTitle", null, defaultLocale));
        String fishierId = request.getParameter("fishier");
        Fishier fishier;
        List < ActivityPeriod > periods =
                BeanGetter.lookupActivityPeriodFacade().findAll();
        List < FishiersElement > elements;
        List < Action > actions =
                BeanGetter.lookupActionFacade().findAll();
        List < FishierElementBridge > fishierElementBridges;

        formInfo.put("periods", periods);
        formInfo.put("actions", actions);

        try {
            elements = this.findElements(fishierId);
            formInfo.put("elements", elements);
            fishier = this.findFishier(fishierId);
            fishierElementBridges =
                    this.findFishierElementBridgeByFishier(fishierId);
        } catch (Exception ex) {
            MPLogger.error("Fishier not found at fishiers edit: " + fishierId);
            ex.printStackTrace();
            formInfo.put("message",
                    localeProvider.getMessage("fishiers.fishierNotFound",
                    null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            return new ModelAndView(this.showList(request, response).
                    getView(), formInfo);
        }
        formInfo.put("fishierElementBridges", fishierElementBridges);
        formInfo.put("fishier", fishier);
        return new ModelAndView("fishiers/details", formInfo);
    }

    /**
     * Method used for editing fishier element for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView editElement(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>

        formInfo = ( HashMap < String, Object > )
                this.details(request, response).getModel();
        Fishier fishier = (Fishier) formInfo.get("fishier");
        String fishierElementBridgeId =
                request.getParameter("fishierElementBridge");
        String changeMileage = request.getParameter("changeMileage");
        String changeDate = request.getParameter("changeDate");

        try {
            FishierElementBridge fishEl =
                    BeanGetter.lookupFishierElementBridgeFacade().
                    find(new Integer(fishierElementBridgeId));
            if (fishEl == null) {
                MPLogger.error("Error while finding"
                        + "fishierelementbridge in Fishiers.editElement");
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

            formInfo.put("elements",
                    this.findElements(fishier.getId().toString()));
            formInfo.put("message",
                    localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColor());
        } catch (NumberFormatException nfe) {
            formInfo.put("message",
                    localeProvider.
                    getMessage("error.parsingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } catch (ParseException ex) {
            formInfo.put("message",
                    localeProvider.
                    getMessage("error.wrongDate", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } catch (Exception exception) {
            MPLogger.error("Error while editing fishier"
                    + "element at Fishiers.editElement");
            exception.printStackTrace();
            formInfo.put("message", localeProvider.
                    getMessage("error.errorWhileEditing", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        } finally {
            formInfo.put("fishierElementBridges",
                    this.findFishierElementBridges());
        }
        return new ModelAndView("fishiers/details", formInfo);
    }

    /**
     * Method used for finding fishier for specified user.
     * @param fishierId fishier id number
     * @return fishier with specified id
     * @throws utils.MPException
     */
    private Fishier findFishier(String fishierId)
            throws MPException {
        for (Fishier fishier : BeanGetter.getUserInfo().getFishiers()) {
            if (fishier.getId().toString().equals(fishierId)) {
                return fishier;
            }
        }
        throw new MPException("Fishier not found at Fishier.findFishier");
    }

    /**
     * Method used for finding fishiers for specified user.
     * @return List which contains all fishiers owned by logged user
     */
    private List<Fishier> findFishiers() {
        List <Fishier> result = null;
        try {
            result = BeanGetter.getUserInfo().getFishiers();
        } catch (MPException mpException) {
            MPLogger.error("Exception caught in Fishiers.findFishiers: "
                    + mpException.getMessage());
        }
        return result;
    }

    /**
     * Methods used for finding FishierElementBridge by given id
     * @param elementId id number of FishierElementBridge to find
     * @return FishierElementBridge element if found
     */
    private FishierElementBridge findFishierElementBridge(String elementId) {
        return BeanGetter.lookupFishierElementBridgeFacade().
                find(Integer.parseInt(elementId));
    }

    /**
     * Method used for finding FishierElement objects NOT connected
     * with specified fishier
     * @param fishierId fishier's id number
     * @return List of FishierElement objects
     */
    private List<FishiersElement> findElements(String fishierId) {
        return BeanGetter.lookupFishiersElementFacade().
                findAllNotConnWithFishier(fishierId);
    }

    /**
     * Method used for finding all FishierElementBridge.
     * @return List of FishierElementBridge objects
     */
    private List <FishierElementBridge> findFishierElementBridges() {
        return BeanGetter.lookupFishierElementBridgeFacade().findAll();
    }

    /**
     * Method used for finding FishierElementBridge objects connected
     * with specified fishier
     * @param fishierId fishier's id number
     * @return List of FishierElementBridge objects
     */
    private List<FishierElementBridge>
            findFishierElementBridgeByFishier(String fishierId) {
        return BeanGetter.lookupFishierElementBridgeFacade().
                findAllByFishier(fishierId);
    }

    /**
     * Method used for finding all Motorcycles for logged user
     * @return List of Motorcycle objects
     */
    private List<Motorcycle> getBikes() {
        List < Motorcycle > bikes = null;
        try {
            bikes = BeanGetter.lookupMotorcycleFacade().
                    findWithoutFishier(BeanGetter.getUserInfo().getUsername());
        } catch (MPException mpException) {
            MPLogger.error("Exception caught in Fishiers.findFishiers: "
                    + mpException.getMessage());
        }
        return bikes;
    }
}
