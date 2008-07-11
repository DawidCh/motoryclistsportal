/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation.controllers.secured;

import com.sun.tools.xjc.generator.bean.BeanGenerator;
import entities.Fishier;
import entities.FishierElementBridge;
import security.DetailedUserInformation;
import entities.Motorcycle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
 * @author Dawid Chojnacki
 */
public class Bikes {

    /**
     * Method used for showing list of bikes which are owned by logged user.
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
        DetailedUserInformation userInfo = BeanGetter.getUserInfo();
        List < Motorcycle > bikes = new ArrayList < Motorcycle >(
                BeanGetter.lookupMotorcycleFacade().
                findByLogin(userInfo.getUsername()));
        formInfo.put("bikes", bikes);
        formInfo.put("pageTitle", localeProvider.
                getMessage("bikes.pageTitle", null, defaultLocale));
        Logger.getLogger("E").trace("Exiting from: showList");
        return new ModelAndView("bikes/list", formInfo);
    }

    /**
     * Method used for adding new bike for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView addBike(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: addBike");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("bikes.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.
                getMessage("bikes.formTitle.add", null, defaultLocale));
        String form = request.getParameter("form");
        formInfo.put("form", form);
        formInfo.put("action", "new.html");
        String message = null;
        ModelAndView result = null;

        Motorcycle newBike;
        if (form != null) {
            boolean wellValidated = this.validateInputForm(request, formInfo);
            if (wellValidated) {
                try {
                    newBike = new Motorcycle(
                        (String) formInfo.get("manufacturer"),
                        (String) formInfo.get("model"),
                        new Integer((String) formInfo.get("year")),
                        new Integer((String) formInfo.get("torque")),
                        new Integer((String) formInfo.get("power")),
                        new Double((String) formInfo.get("mileage")),
                        new Integer((String) formInfo.get("displacement")),
                        (String) formInfo.get("nickname"),
                        BeanGetter.getUserInfo().getUser());
                    BeanGetter.lookupMotorcycleFacade().create(newBike);
                    message = localeProvider.getMessage(
                            "success", null, defaultLocale);
                    formInfo.put("formTitle", localeProvider.
                    getMessage(
                    "bikes.formTitle.edit", null, defaultLocale));
                    formInfo.put("action", "edit.html");
                    formInfo.put("messColor", DefaultValues.getSuccColour());
                    formInfo.put("bike", newBike.getId());
                    formInfo.putAll(this.createHashMapFromBike(newBike));
                } catch (NumberFormatException numberFormatException) {
                    message = localeProvider.
                            getMessage("error.parsingError", null,
                            defaultLocale);
                    formInfo.put("messColor",
                            DefaultValues.getFailColour());
                    Logger.getLogger("E").debug("Error while number parsing");
                } catch (Exception exception) {
                    message = localeProvider.getMessage(
                            "error.errorWhileAdding",
                            null, defaultLocale);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    Logger.getLogger("E").
                            error(
                            "Error while persisting in db at Bikes.add: ");
                    exception.printStackTrace();
                } finally {
                    formInfo.put("message", message);
                    result = new ModelAndView("bikes/add", formInfo);
                }
            } else {
                formInfo.put("form", form);
            }
        }
        if (result == null) {
            result = new ModelAndView("bikes/add", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: addBike");
        return new ModelAndView("bikes/add", formInfo);
    }

    /**
     * Method used for deleting assigning fishier to bike for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView reassignFishier(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: reassignFishier");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle",
                null, defaultLocale));
        String message = new String();
        String bikeId = request.getParameter("bike");
        try {
            String fishier = request.getParameter("fishier");
            formInfo.put("fishier", fishier);
            if (bikeId == null || bikeId.isEmpty() || fishier == null
                    || fishier.isEmpty()) {
                throw new Exception();
            }
            Motorcycle bikeObject = BeanGetter.lookupMotorcycleFacade().
                    find(Integer.parseInt(bikeId));
            formInfo.put("bike", bikeObject);
            Fishier fish = bikeObject.getFishier();
            if (fish == null || !fish.getId().equals(new Integer(fishier))) {
                throw new Exception(
                        "Fishier not found at Bikes.reaasignFishier");
            }
            fish.setMotorcycle(null);
            bikeObject.setFishier(null);
            BeanGetter.lookupMotorcycleFacade().edit(bikeObject);
            BeanGetter.lookupFishierFacade().edit(fish);
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColour());
            formInfo.putAll(this.showList(request, response).getModel());
        } catch (Exception ex) {
            Logger.getLogger("E").error("Error while reassigning fishier to"
                    + "bike in Bikes.reassignFishier");
            message = localeProvider.getMessage("error.otherError",
                    null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getFailColour());
            formInfo.putAll(this.showList(request, response).getModel());
        }
        Logger.getLogger("E").trace("Exiting from: reassignFishier");
        return new ModelAndView("bikes/details", formInfo);
    }

    /**
     * Method used for editing bike for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView editBike(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: editBike");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("formTitle", localeProvider.
                getMessage("bikes.formTitle.edit", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        String form = null;
        Motorcycle bike = null;
        String message = null;
        ModelAndView result = null;

        try {
            bike = BeanGetter.lookupMotorcycleFacade().
                    find(new Integer(bikeId));
            form = request.getParameter("form");
            formInfo.put("bike", bike.getId());
            formInfo.put("action", "edit.html");
            formInfo.putAll(this.createHashMapFromBike(bike));
        } catch (Exception exception) {
            Logger.getLogger("E").
                    error("Bike not found at bikes edit: " + bikeId);
            formInfo.clear();
            formInfo.putAll(this.showList(request, response).getModel());
            message = localeProvider.getMessage("bikes.bikeNotFound",
                    null, defaultLocale);
            formInfo.put("messColor", DefaultValues.getFailColour());
            result = new ModelAndView("bikes/list", formInfo);
            exception.printStackTrace();
        }
        if (form != null) {
            boolean wellValidated = this.validateInputForm(request, formInfo);
            if (wellValidated) {
                try {
                    Motorcycle fromRequest =
                        this.createMotorcycleFromValidatedRequets(request);
                    this.prepareBike(fromRequest, bike);
                    BeanGetter.lookupMotorcycleFacade().edit(bike);
                    message = localeProvider.
                        getMessage("success", null, defaultLocale);
                    formInfo.put("messColor", DefaultValues.getSuccColour());
                    formInfo.putAll(this.createHashMapFromBike(bike));
                } catch (NumberFormatException numberFormatException) {
                    message = localeProvider.
                            getMessage("error.parsingError", null,
                            defaultLocale);
                    formInfo.put("messColor",
                            DefaultValues.getFailColour());
                } catch (Exception exception) {
                    Logger.getLogger("E").error("Error while persisting bike");
                    message = localeProvider.
                            getMessage("error.errorWhileAdding",
                            null, defaultLocale);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    exception.printStackTrace();
                }
            } else {
                formInfo.put("form", form);
                result = new ModelAndView("bikes/add", formInfo);
            }
        }
        formInfo.put("message", message);
        if (result == null) {
            result = new ModelAndView("bikes/add", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: editBike");
        return result;
    }

    /**
     * Method used for deleting bike for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView deleteBike(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: deleteBike");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("bikes.pageTitle", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        ModelAndView result = null;
        Map map = null;
        if (bikeId == null) {
            Logger.getLogger("E").error("Null bike id at deleteBike");
            formInfo.put("errorMessage", localeProvider.
                    getMessage("error.otherError", null, defaultLocale));
            result = new ModelAndView("unsecured/error", formInfo);
        } else {
            Motorcycle bikeToDel = null;
            try {
                bikeToDel = MPUtilities.findBike(bikeId);
                BeanGetter.lookupMotorcycleFacade().remove(bikeToDel);
                map = this.showList(request, response).getModel();
                map.put("message", localeProvider.
                    getMessage("success", null, defaultLocale));
                map.put("messColor", DefaultValues.getSuccColour());
            } catch (Exception mPException) {
                map = this.showList(request, response).getModel();
                map.put("message", localeProvider.getMessage(
                        "error.errorWhileDeleting", null, defaultLocale));
                map.put("messColor", DefaultValues.getFailColour());
                mPException.printStackTrace();
            } finally {
                result = new ModelAndView("/bikes/list", map);
            }
        }
        Logger.getLogger("E").trace("Exiting from: deleteBike");
        return result;
    }

    /**
     * Method used for display bike for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView details(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: details");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("bikes.pageTitle", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        Motorcycle bike = null;
        ModelAndView result = null;
        try {
            bike = MPUtilities.findBike(bikeId);
            if (bike == null) {
                throw new MPException("Bike not found at details");
            }
            formInfo.put("bike", bike);
        } catch (MPException exception) {
            Logger.getLogger("E").error("Bike not found");
            formInfo.put("messColor", DefaultValues.getFailColour());
            formInfo.put("errorMessage", localeProvider.
                    getMessage("bikes.bikeNotFound", null, defaultLocale));
            result = new ModelAndView("unsecured/error", formInfo);
        }
        if (result == null) {
            result = new ModelAndView("bikes/details", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: details");
        return result;
    }

    /**
     * Method used for assign specified fishier to specified bike
     * for specified user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView assignFishier(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: assignFishier");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("bikes.pageTitle", null, defaultLocale));
        String form = request.getParameter("form");
        String message = new String();
        String bike = request.getParameter("bike");
        formInfo.put("bike", bike);
        ModelAndView result = null;
        if (form != null) {
            try {
                String fishier = request.getParameter("fishier");
                formInfo.put("fishier", fishier);
                if (bike == null || bike.isEmpty()
                        || fishier == null || fishier.isEmpty()) {
                    Logger.getLogger("E").
                            debug("Bike not found at Bikes.assignFishier");
                    throw new Exception();
                }
                Motorcycle bikeObject = BeanGetter.lookupMotorcycleFacade().
                        find(Integer.parseInt(bike));
                Fishier fish = MPUtilities.findFishier(fishier);
                Fishier fishierToAssign;
                if (fish.getMotorcycle() != null) {
                    fishierToAssign = new Fishier(fish);
                    fishierToAssign.setMotorcycle(bikeObject);
                    BeanGetter.lookupFishierFacade().create(fishierToAssign);
                    this.assignFishierElementBridgesToFishier(
                            fishierToAssign, bikeObject, fish);
                } else {
                    fishierToAssign = fish;
                    fishierToAssign.setMotorcycle(bikeObject);
                    BeanGetter.lookupFishierFacade().edit(fishierToAssign);
                }
                bikeObject.setFishier(fishierToAssign);
                BeanGetter.lookupMotorcycleFacade().edit(bikeObject);

                message = localeProvider.getMessage("success",
                        null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getSuccColour());
                formInfo.putAll(this.showList(request, response).getModel());
            } catch (Exception exception) {
                Logger.getLogger("E").
                        error("Error while assigning fishier to bike"
                        + "in Bikes.assignFishier");
                message = localeProvider.getMessage("error.otherError",
                        null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColour());
                formInfo.putAll(this.showList(request, response).getModel());
            } finally {
                result = new ModelAndView("bikes/list", formInfo);
            }
        } else {
            List < Fishier > fishiers = MPUtilities.findFishiers();
            formInfo.put("fishiers", fishiers);
        }
        if (result == null) {
            result = new ModelAndView("bikes/selectFishier", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: assignFishier");
        return result;
    }

    /**
     * Method used for process changes in the existing bike object.
     * @param fromRequest Motorcycle object with validated data from user form
     * @param bike bike existing in the db
     */
    private void prepareBike(Motorcycle fromRequest, Motorcycle bike) {
        Logger.getLogger("E").trace("Entering to: prepareBike");
        if (!fromRequest.getNickname().equals(bike.getNickname())) {
            bike.setNickname(fromRequest.getNickname());
        }
        if (!fromRequest.getManufacturer().equals(
                bike.getManufacturer())) {
            bike.setManufacturer(fromRequest.getManufacturer());
        }
        if (!fromRequest.getModel().equals(bike.getModel())) {
            bike.setModel(fromRequest.getModel());
        }
        if (fromRequest.getYear() != bike.getYear()) {
            bike.setYear(fromRequest.getYear());
        }
        if (fromRequest.getTorque() != bike.getTorque()) {
            bike.setTorque(fromRequest.getTorque());
        }
        if (fromRequest.getPower() != bike.getPower()) {
            bike.setPower(fromRequest.getPower());
        }
        if (fromRequest.getEnginecapacity() != bike.
                getEnginecapacity()) {
            bike.setEnginecapacity(
                    fromRequest.getEnginecapacity());
        }
        if (fromRequest.getMileage() != bike.getMileage()) {
            bike.setMileage(fromRequest.getMileage());
        }
        Logger.getLogger("E").trace("Exiting from: prepareBike");
    }

    /**
     * Method used for validating user input data.
     * @param request HTTP request
     * @param formInfo form with user data
     * @return true if form is well validated, false otherwise
     */
    private boolean validateInputForm(
            HttpServletRequest request, HashMap < String, Object > formInfo) {
        Logger.getLogger("E").trace("Entering to: validateInputForm");
        String message = null;
        boolean result = true;
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        //<editor-fold default-state="collapsed" desc="Obtaining info from request">
        String action = request.getParameter("action");
        HashMap < String, Object > formData = new HashMap < String, Object >();
        formData.put("nickname", request.getParameter("nickname"));
        formData.put("manufacturer", request.getParameter("manufacturer"));
        formData.put("model", request.getParameter("model"));
        formData.put("year", request.getParameter("year"));
        formData.put("torque", request.getParameter("torque"));
        formData.put("power", request.getParameter("power"));
        formData.put("mileage", request.getParameter("mileage"));
        formData.put("displacement", request.getParameter("displacement"));
        //</editor-fold>
        try {
            for (Iterator it = formData.keySet().iterator(); it.hasNext();) {
                String currentKey = (String) it.next();
                String requestKey = request.getParameter(currentKey);
                if (!currentKey.equals("nickname")
                        && !currentKey.equals("model")
                        && !currentKey.equals("manufacturer")) {
                    try {
                        Double.parseDouble(requestKey);
                    } catch (NumberFormatException numberFormatException) {
                        formData.put(currentKey, null);
                        message = localeProvider.
                            getMessage("error.parsingError",
                            null, defaultLocale);
                        throw new MPException("Error while parsing "
                            + action + " bike: "
                            + currentKey);
                    }
                }
                if (requestKey == null
                        || requestKey.isEmpty()) {
                    formData.put(currentKey, null);
                    message = localeProvider.
                    getMessage("error.notAllFilled", null, defaultLocale);
                    throw new MPException("Not all fields filled in "
                            + action + " bike: "
                            + currentKey);
                }
            }
        } catch (MPException mpException) {
            formData.put("message", message);
            formData.put("messColor", DefaultValues.getFailColour());
            result = false;
            Logger.getLogger("E").debug(mpException.getMessage());
        }
        formInfo.putAll(formData);
        Logger.getLogger("E").trace("Exiting from: validateInputForm");
        return result;
    }

    /**
     * Method used for creating hashmap from Motorcycle object.
     * @param bike object to convert
     * @return HashMap with bike fields
     */
    private HashMap < String, Object > createHashMapFromBike(Motorcycle bike) {
        Logger.getLogger("E").trace("Entering to: createHashMapFromBike");
        HashMap < String, Object > result = new HashMap < String, Object >();
        result.put("nickname", bike.getNickname());
        result.put("manufacturer", bike.getManufacturer());
        result.put("model", bike.getModel());
        result.put("year", bike.getYear());
        result.put("torque", bike.getTorque());
        result.put("power", bike.getPower());
        result.put("mileage", bike.getMileage());
        result.put("displacement", bike.getEnginecapacity());
        Logger.getLogger("E").trace("Exiting from: createHashMapFromBike");
        return result;
    }

    /**
     * Method used for creating Motorcycle object from http request.
     * @param request HTTP request objecy
     * @return Motorcycle object newly created
     */
    private Motorcycle createMotorcycleFromValidatedRequets(HttpServletRequest
            request) throws NumberFormatException {
        Logger.getLogger("E").
                trace("Entering to: createMotorcycleFromValidatedRequets");
        Motorcycle result = new Motorcycle();
        result.setNickname(request.getParameter("nickname"));
        result.setManufacturer(request.getParameter("manufacturer"));
        result.setModel(request.getParameter("model"));
        result.setYear(Integer.parseInt(request.getParameter("year")));
        result.setTorque(Integer.parseInt(request.getParameter("torque")));
        result.setPower(Integer.parseInt(request.getParameter("power")));
        result.setMileage(Double.parseDouble(request.getParameter("mileage")));
        result.setEnginecapacity(Integer.parseInt(
                request.getParameter("displacement")));
        Logger.getLogger("E").
                trace("Exiting from: createMotorcycleFromValidatedRequets");
        return result;
    }

    /**
     * Method used for assigning and creating new fishier element bridges
     * (taken from existing fishier) to new fishier.
     * @param newFishier fishier to which elements bridges will be assigned
     * @param bike bike connected with fishier
     * @param oldFishier fishier from fishier elements bridges will be taken
     */
    private void assignFishierElementBridgesToFishier(Fishier newFishier,
            Motorcycle bike,  Fishier oldFishier) {
        Logger.getLogger("E").
                trace("Entering to: assignFishierElementBridgesToFishier");
        List < FishierElementBridge > fishElBridgeColl =
                new ArrayList < FishierElementBridge >();
        FishierElementBridge element;
        for (Iterator it = oldFishier.getFishierElementBridgeCollection().
                iterator(); it.hasNext();) {
            element = new FishierElementBridge((FishierElementBridge)
                    it.next());
            element.setChangemileage(bike.getMileage());
            element.setFishier(newFishier);
            element.setChangedate(Calendar.getInstance().getTime());
            BeanGetter.lookupFishierElementBridgeFacade().
                    create(element);
            fishElBridgeColl.add(element);
        }
        newFishier.setFishierElementBridgeCollection(fishElBridgeColl);
        Logger.getLogger("E").
                trace("Exiting from: assignFishierElementBridgesToFishier");
    }
}
