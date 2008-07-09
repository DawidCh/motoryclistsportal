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
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPException;
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
        Logger.getLogger("E").trace("Entering to: showList");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        List < Fishier > fishiers = MPUtilities.findFishiers();
        formInfo.put("pageTitle", localeProvider.getMessage(
                "fishiers.pageTitle", null, defaultLocale));
        formInfo.put("fishiers", fishiers);
        Logger.getLogger("E").trace("Exiting from: showList");
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
        Logger.getLogger("E").trace("Entering to: addFishier");
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
        ModelAndView result = null;
        try {
            if (bikeId == null) {
                throw new MPException();
            }
            bike = MPUtilities.findBike(bikeId);
            formInfo.put("bike", bike);
        } catch (MPException mPException) {
            form = null;
            Logger.getLogger("E").error("Bike not found in Fishiers.add");
        }
        List < Motorcycle > bikes = MPUtilities.findBikesWithoutFishier();
        formInfo.put("bikes", bikes);
        String message;

        if (form != null) {
            boolean wellValidated = this.validateForm(request, formInfo);
            if (wellValidated) {
                Fishier newFishier = new Fishier();
                newFishier.setDescription(request.getParameter("description"));
                newFishier.setLogin(BeanGetter.getUserInfo().getUser());
                newFishier.setMotorcycle(bike);
                bike.setFishier(newFishier);
                try {
                    BeanGetter.lookupFishierFacade().create(newFishier);
                    BeanGetter.lookupMotorcycleFacade().edit(bike);
                    formInfo.put("fishier", newFishier);
                    formInfo.put("action", "edit.html");
                    message = localeProvider.
                            getMessage("success", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getSuccColour());
                    formInfo.put("formTitle", localeProvider.getMessage(
                            "fishiers.formTitle.edit", null, defaultLocale));
                } catch (Exception exception) {
                    message = localeProvider.getMessage(
                            "error.errorWhileAdding", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    formInfo.put("action", "new.html");
                    Logger.getLogger("E").
                            error("Error wihle persisting in db at "
                            + "Fishiers.add: "
                            + exception.getMessage());
                }
            }
        }
        formInfo.put("action", "new.html");
        result = new ModelAndView("fishiers/add", formInfo);
        Logger.getLogger("E").trace("Exiting from: addFishier");
        return result;
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
        Logger.getLogger("E").trace("Entering to: editFishier");
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
        Fishier fishier = null;
        String message = null;
        ModelAndView result = null;

        List < Motorcycle > bikes = MPUtilities.findBikesWithoutFishier();
        formInfo.put("bikes", bikes);

        try {
            fishier = MPUtilities.findFishier(fishierId);
            formInfo.put("fishier", fishier);
        } catch (Exception ex) {
            Logger.getLogger("E").error("Fishier not found at fishiers edit: "
                    + fishierId);
            HashMap < String, Object > map = new HashMap < String, Object >();
            map.put("message", localeProvider.getMessage(
                    "fishiers.fishierNotFound", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColour());
            ex.printStackTrace();
            result = new ModelAndView(this.showList(request, response).
                    getView(), map);
            form = null;
        }

        if (form != null) {
            boolean wellValidated = this.validateForm(request, formInfo);
            if (wellValidated) {
                try {
                    String description = request.getParameter("description");
                    if (!description.equals(fishier.getDescription())) {
                        fishier.setDescription(description);
                        BeanGetter.lookupFishierFacade().edit(fishier);
                    }
                    message = localeProvider.
                            getMessage("success", null, defaultLocale);
                    formInfo.put("messColor", DefaultValues.getSuccColour());
                } catch (Exception exception) {
                    message = localeProvider.
                            getMessage("error.errorWhileAdding",
                            null, defaultLocale);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    Logger.getLogger("E").error("Error wihle persisting"
                            + "in db at Fishiers.edit: "
                            + exception.getMessage());
                    exception.printStackTrace();
                } finally {
                    formInfo.put("message", message);
                }
            }
        }
        if (result == null) {
            result = new ModelAndView("fishiers/add", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: editFishier");
        return result;
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
        Logger.getLogger("E").trace("Entering to: deleteFishier");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage(
                "fishiers.pageTitle", null, defaultLocale));
        String fishierId = request.getParameter("fishier");
        String bikeId = request.getParameter("bike");
        ModelAndView result = null;
        Fishier fishier = null;
        String message;

        try {
            fishier = MPUtilities.findFishier(fishierId);
            try {
                List < Motorcycle > bikes =
                        BeanGetter.lookupMotorcycleFacade().findByFishier(fishier);
                for (Iterator < Motorcycle > it = bikes.iterator(); it.hasNext();) {
                    Motorcycle motorcycle = it.next();
                    motorcycle.setFishier(null);
                    BeanGetter.lookupMotorcycleFacade().edit(motorcycle);
                }
                BeanGetter.lookupFishierFacade().remove(fishier);
                formInfo.put("message", localeProvider.getMessage(
                        "success", null, defaultLocale));
                formInfo.put("messColor", DefaultValues.getSuccColour());
                if (bikeId != null) {
                    formInfo.put("bike", bikeId);
                    result = new ModelAndView("redirect:/bikes/details.html",
                            formInfo);
                } else {
                    formInfo.put("fishiers", MPUtilities.findFishiers());
                    result = new ModelAndView("fishiers/list", formInfo);
                }
            } catch (Exception exception) {
                message = localeProvider.getMessage(
                        "error.errorWhileDeleting", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColour());
                Logger.getLogger("E").
                        error("Error wihle deleting in db at Fishiers.delete: "
                        + exception.getMessage());
                if (bikeId != null) {
                    formInfo.put("bike", bikeId);
                    result = new ModelAndView(
                            "redirect:/bikes/details.html", formInfo);
                } else {
                    formInfo.put("fishiers", MPUtilities.findFishiers());
                    result = new ModelAndView("fishiers/list", formInfo);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger("E").error("Fishier not found at fishiers edit: ");
            formInfo.put("message", localeProvider.getMessage(
                    "fishiers.fishierNotFound", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            ex.printStackTrace();
            if (bikeId != null) {
                formInfo.put("bike", bikeId);
                result = new ModelAndView(
                        "redirect:/bikes/details.html", formInfo);
            } else {
                formInfo.put("fishiers", MPUtilities.findFishiers());
                result = new ModelAndView(
                        this.details(request, response).getView(), formInfo);
            }
        }
        Logger.getLogger("E").trace("Exiting from: deleteFishier");
        return result;
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
        Logger.getLogger("E").trace("Entering to: deleteElement");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        String elementId = request.getParameter("element");
        FishierElementBridge elementToDel =
                MPUtilities.findFishierElementBridge(elementId);
        try {
            if (elementToDel == null) {
                throw new Exception();
            }
            BeanGetter.lookupFishierElementBridgeFacade().remove(elementToDel);
            formInfo.put("message", localeProvider.getMessage(
                    "success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColour());
        } catch (Exception exception) {
            Logger.getLogger("E").
                    error("Error while deleting fishier element at"
                    + "Fishiers.deleteElement: " + elementId);
            exception.printStackTrace();
            formInfo.put(
                    "message", localeProvider.getMessage(
                    "error.errorWhileDeleting", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
        }
        formInfo = ( HashMap < String, Object > ) this.details(
                request, response).getModel();
        Logger.getLogger("E").trace("Exiting from: deleteElement");
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
        Logger.getLogger("E").trace("Entering to: addElement");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>

        formInfo = (HashMap < String, Object > )
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
                Logger.getLogger("E").error(
                        "Error while finding"
                        + "fishierelement in Fishiers.addElement");
                throw new Exception();
            }
            if (fishier == null) {
                Logger.getLogger("E").error(
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
                    MPUtilities.findElementsNotConnWithFisher(
                    fishier.getId().toString()));
            formInfo.put("message",
                    localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor",
                    DefaultValues.getSuccColour());
            formInfo.put("fishierElementBridges",
                    MPUtilities.findFishierElementBridges());
        } catch (NumberFormatException nfe) {
            formInfo.put("periodLength", null);
            formInfo.put("changeMileage", null);
            formInfo.put("changeDate", changeDate);
            formInfo.put("message",
                    localeProvider.getMessage(
                    "error.parsingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            formInfo.put("periodLength", periodLength);
        } catch (ParseException ex) {
            formInfo.put("date", null);
            formInfo.put("message",
                    localeProvider.getMessage(
                    "error.wrongDate", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
        } catch (Exception exception) {
            Logger.getLogger("E").error("Error while adding fishier"
                    + "element at Fishiers.addElement");
            exception.printStackTrace();
            formInfo.put("message", localeProvider.
                    getMessage("error.errorWhileAdding", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            formInfo.put("periodLength", periodLength);
        }
        Logger.getLogger("E").trace("Exiting from: addElement");
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
        Logger.getLogger("E").trace("Entering to: details");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("fishiers.pageTitle", null, defaultLocale));
        String fishierId = request.getParameter("fishier");
        Fishier fishier = null;
        List < ActivityPeriod > periods =
                BeanGetter.lookupActivityPeriodFacade().findAll();
        List < FishiersElement > elements;
        List < Action > actions =
                BeanGetter.lookupActionFacade().findAll();
        List < FishierElementBridge > fishierElementBridges = null;
        ModelAndView result = null;

        formInfo.put("periods", periods);
        formInfo.put("actions", actions);

        try {
            elements = MPUtilities.findElementsNotConnWithFisher(fishierId);
            formInfo.put("elements", elements);
            fishier = MPUtilities.findFishier(fishierId);
            fishierElementBridges =
                    MPUtilities.findFishierElementBridgeByFishier(fishierId);
        } catch (Exception ex) {
            Logger.getLogger("E").
                    error("Fishier not found at fishiers edit: " + fishierId);
            ex.printStackTrace();
            formInfo.put("message",
                    localeProvider.getMessage("fishiers.fishierNotFound",
                    null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            result = new ModelAndView(this.showList(request, response).
                    getView(), formInfo);
        }
        formInfo.put("fishierElementBridges", fishierElementBridges);
        formInfo.put("fishier", fishier);
        if (result == null) {
            result = new ModelAndView("fishiers/details", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: details");
        return result;
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
        Logger.getLogger("E").trace("Entering to: editElement");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>

        formInfo = (HashMap < String, Object >)
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
                Logger.getLogger("E").error("Error while finding"
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
                    MPUtilities.findElementsNotConnWithFisher(
                    fishier.getId().toString()));
            formInfo.put("message",
                    localeProvider.getMessage("success", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getSuccColour());
        } catch (NumberFormatException nfe) {
            formInfo.put("message",
                    localeProvider.
                    getMessage("error.parsingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
        } catch (ParseException ex) {
            formInfo.put("message",
                    localeProvider.
                    getMessage("error.wrongDate", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
        } catch (Exception exception) {
            Logger.getLogger("E").error("Error while editing fishier"
                    + "element at Fishiers.editElement");
            exception.printStackTrace();
            formInfo.put("message", localeProvider.
                    getMessage("error.errorWhileEditing", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
        } finally {
            formInfo.put("fishierElementBridges",
                    MPUtilities.findFishierElementBridges());
        }
        Logger.getLogger("E").trace("Exiting from: editElement");
        return new ModelAndView("fishiers/details", formInfo);
    }

    /**
     * Method used for validating input form.
     * @param request HTTP request object
     * @param formInfo data from user's form
     * @return true if user typed data correctly, false otherwise
     */
    private boolean validateForm(HttpServletRequest request,
            HashMap < String, Object > formInfo) {
        Logger.getLogger("E").trace("Entering to: validateForm");
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        String message;
        String description = request.getParameter("description");
        boolean result = true;
        if (description == null || description.length() == 0) {
            Logger.getLogger("E").
                    error("Not all fields filled in Fishiers.add");
            message = localeProvider.getMessage(
                    "notAllFilled", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getFailColour());
            formInfo.put("action", "new.html");
            result = false;
        }
        Logger.getLogger("E").trace("Exiting from: validateForm");
        return result;
    }
}
