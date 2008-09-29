/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation.controllers.secured;

import ai.FuzzyDriver;
import ai.fuzzyficators.TripsFuzzyficator;
import entities.Distance;
import fuzzyelements.Fuzzyficable;
import entities.Motorcycle;
import entities.Trip;
import entities.TripType;
import fuzzyelements.FuzzyValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import utils.ApplicationSettings;
import utils.LocaleProvider;
import utils.MPException;
import utils.MPUtilities;

/**
 *
 * @author Dawid Chojnacki
 */
public class Trips {
    /**
     * Method used for showing list of trips which are owned by logged user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView showList(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: showList");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        List < Fuzzyficable > trips =
                new ArrayList < Fuzzyficable > (MPUtilities.findTrips());
        Distance fuzzyAverageDistance = FuzzyDriver.getFuzzyUserDistance();
        List < FuzzyValue > fuzzyTripDistanceCollection =
                new TripsFuzzyficator().
                    processCollection(trips);
        formInfo.put("fuzzyTripLength", fuzzyTripDistanceCollection);
        formInfo.put("fuzzyAverageValue",
                fuzzyAverageDistance.getDescription());
        formInfo.put("trips", trips);
        formInfo.put("pageTitle", localeProvider.
                getMessage("trips.pageTitle", null, defaultLocale));
        Logger.getLogger("errorLogger").trace("Exiting from: showList");
        return new ModelAndView("trips/list", formInfo);
    }

    /**
     * Method used for adding trips.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView addTrip(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: addTrip");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("trips.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.
                getMessage("trips.formTitle.add", null, defaultLocale));
        String form = request.getParameter("form");
        formInfo.put("form", form);
        String message = null;

        List < TripType > tripTypes =
                BeanGetter.lookupTripTypeFacade().findAll();
        List < Motorcycle > bikes = BeanGetter.lookupMotorcycleFacade().
                findByLogin(BeanGetter.getUserInfo().getUsername());
        formInfo.put("types", tripTypes);
        formInfo.put("bikes", bikes);
        formInfo.put("action", "new.html");
        if (form != null) {
            boolean wellValidated = this.validateForm(formInfo, request);
            if (wellValidated) {
                Motorcycle bike = MPUtilities.findBike(
                        (String) formInfo.get("bike"));
                if (bike == null) {
                    Logger.getLogger("errorLogger").
                            error("Bike not found at Trips add: "
                            + (String) formInfo.get("bike"));
                    message = localeProvider.getMessage("trips.bikeNotFound",
                            null, defaultLocale);
                    formInfo.put("messColor", ApplicationSettings.getFailColour());
                } else {
                    try {
                        this.createTrip(formInfo, request, bike);
                        message = localeProvider.getMessage("success", null,
                                defaultLocale);
                        formInfo.put("messColor",
                                ApplicationSettings.getSuccColour());
                        formInfo.put("action", "edit.html");
                    } catch (ParseException parseException) {
                        formInfo.put("date", null);
                        message = localeProvider.
                                getMessage("error.wrongDate", null,
                                defaultLocale);
                        formInfo.put("messColor",
                                ApplicationSettings.getFailColour());
                    } catch (NumberFormatException numberFormatException) {
                        formInfo.put("distance", null);
                        message = localeProvider.
                                getMessage("error.parsingError", null,
                                defaultLocale);
                        formInfo.put("messColor",
                                ApplicationSettings.getFailColour());
                    } catch (Exception exception) {
                        message = localeProvider.
                                getMessage("error.errorWhileAdding",
                                null, defaultLocale);
                        formInfo.put("messColor",
                                ApplicationSettings.getFailColour());
                        Logger.getLogger("errorLogger").
                                error("Error wihle persisting in db "
                                + "at Trip.add: "
                                + exception.getMessage());
                    }
                }
            }
            formInfo.put("message", message);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: addTrip");
        return new ModelAndView("trips/add", formInfo);
    }

    /**
     * Method used for editing trips.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView editTrip(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: editTrip");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.
                getMessage("trips.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.
                getMessage("trips.formTitle.edit", null, defaultLocale));
        String form = request.getParameter("form");
        String tripId = request.getParameter("trip");
        String message = null;
        Trip trip = null;
        ModelAndView result = null;
        List < TripType > tripTypes =
                BeanGetter.lookupTripTypeFacade().findAll();
        List < Motorcycle > bikes = BeanGetter.lookupMotorcycleFacade().
                findByLogin(BeanGetter.getUserInfo().getUsername());
        formInfo.put("types", tripTypes);
        formInfo.put("bikes", bikes);
        formInfo.put("form", form);
        try {
            trip = BeanGetter.lookupTripFacade().find(new Integer(tripId));
        } catch (Exception exception) {
            Logger.getLogger("errorLogger").
                    error("Trip not found at trips edit: " + tripId);
            Map map = this.showList(request, response).getModel();
            map.put("message", localeProvider.getMessage("trips.tripNotFound",
                    null, defaultLocale));
            map.put("messColor", ApplicationSettings.getFailColour());
            exception.printStackTrace();
            form = null;
            result = new ModelAndView("trips/list", map);
        }
        if (form != null) {
            boolean wellValidated = this.validateForm(formInfo, request);
            if (wellValidated) {
                try {
                    this.editTrip(trip, formInfo, request);
                    BeanGetter.lookupTripFacade().edit(trip);
                    formInfo.put("messColor", ApplicationSettings.getSuccColour());
                    message = localeProvider.getMessage("success",
                            null, defaultLocale);
                } catch (NumberFormatException ex) {
                    formInfo.put("distance", null);
                    message = localeProvider.
                            getMessage("error.parsingError", null,
                            defaultLocale);
                    formInfo.put("messColor", ApplicationSettings.getFailColour());
                } catch (ParseException ex) {
                    formInfo.put("date", null);
                    message = localeProvider.getMessage("error.wrongDate", null,
                            defaultLocale);
                    formInfo.put("messColor", ApplicationSettings.getFailColour());
                } catch (Exception exception) {
                    message = localeProvider.
                            getMessage("error.errorWhileAdding",
                            null, defaultLocale);
                    formInfo.put("messColor", ApplicationSettings.getFailColour());
                    Logger.getLogger("errorLogger").
                            error("Error wihle persisting in db at Trip.add: "
                            + exception.getMessage());
                }
                formInfo.put("message", message);
            } else {
                result = new ModelAndView("trips/add", formInfo);
            }
        }
        if (result == null) {
            formInfo.put("trip", trip.getId());
            formInfo.put("title", trip.getTitle());
            formInfo.put("type", trip.getType().getDescription());
            formInfo.put("description", trip.getDescription());
            formInfo.put("date", trip.getDate());
            formInfo.put("distance", trip.getDistance());
            formInfo.put("bike", trip.getBike().getId());
            formInfo.put("action", "edit.html");
            result = new ModelAndView("trips/add", formInfo);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: editTrip");
        return result;
    }

    /**
     * Method used for deleting trips.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView deleteTrip(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: deleteTrip");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle",
                null, defaultLocale));
        String tripId = request.getParameter("trip");
        ModelAndView result = null;
        if (tripId == null) {
            Logger.getLogger("errorLogger").error("Null trip id at Trips.deleteTrip");
            formInfo.put("errorMessage", localeProvider.
                    getMessage("error.otherError", null, defaultLocale));
            result = new ModelAndView("unsecured/error", formInfo);
        }
        Map map = null;
        Trip tripToDel = null;
        try {
            tripToDel = MPUtilities.findTrip(tripId);
            BeanGetter.lookupTripFacade().remove(tripToDel);
            FuzzyDriver.recalculateUserDistance();
            map = this.showList(request, response).getModel();
            map.put("message", localeProvider.
                getMessage("success", null, defaultLocale));
            map.put("messColor", ApplicationSettings.getSuccColour());
        } catch (Exception mPException) {
            map = this.showList(request, response).getModel();
            map.put("message", localeProvider.
                    getMessage("error.errorWhileDeleting",
                    null, defaultLocale));
            map.put("messColor", ApplicationSettings.getFailColour());
            mPException.printStackTrace();
        }
        if (result == null) {
            result = new ModelAndView("/trips/list", map);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: deleteTrip");
        return result;
    }

    /**
     * Method used for showing details of selected trips
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView details(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: details");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object >();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle",
                null, defaultLocale));
        String tripId = request.getParameter("trip");
        ModelAndView result = null;
        Trip trip = null;
        try {
            trip = MPUtilities.findTrip(tripId);
            if (trip == null) {
                throw new Exception("Trip not found at Trip.details");
            }
            formInfo.put("trip", trip);
        } catch (Exception ex) {
            Logger.getLogger("errorLogger").error("Trip not found");
            formInfo.put("errorMessage", localeProvider.getMessage(
                    "trips.tripNotFound", null, defaultLocale));
            ex.printStackTrace();
            result = new ModelAndView(this.showList(request, response).
                    getView(), formInfo);
        }
        if (result == null) {
            result = new ModelAndView("trips/details", formInfo);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: details");
        return result;
    }

    /**
     * Method used for editing trip and persisting in db.
     * @param trip trip to edit
     * @param formInfo data from user
     * @param request HTTP request
     * @throws java.text.ParseException
     * @throws utils.MPException
     */
    private void editTrip(Trip trip, HashMap < String, Object > formInfo,
            HttpServletRequest request) throws ParseException, MPException {
        Logger.getLogger("errorLogger").trace("Entering to: editTrip");
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        String message = null;
        double doubleDistance = Double.parseDouble((String)
                formInfo.get("distance"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = sdf.parse((String) formInfo.get("date"));
        Motorcycle bike = MPUtilities.findBike((String) formInfo.get("bikeId"));
        if (bike == null) {
            Logger.getLogger("errorLogger").
                    error("Bike not found at Trips add: "
                    + (String) formInfo.get("bikeId"));
            message = localeProvider.getMessage("trips.bikeNotFound",
                    null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", ApplicationSettings.getFailColour());
        }
        if (!trip.getBike().getId().equals((String) formInfo.get("bikeId"))) {
            trip.setBike(bike);
        }
        if (!trip.getDate().equals(newDate)) {
            trip.setDate(newDate);
        }
        if (!trip.getDescription().equals((String)
                formInfo.get("description"))) {
            trip.setDescription((String) formInfo.get("description"));
        }
        if (trip.getDistance() != doubleDistance) {
            trip.setDistance(doubleDistance);
            FuzzyDriver.recalculateUserDistance();
        }
        if (!trip.getTitle().equals((String) formInfo.get("title"))) {
            trip.setTitle((String) formInfo.get("title"));
        }
        if (!trip.getType().getDescription().
                equals((String) formInfo.get("type"))) {
            trip.setType(BeanGetter.
                    lookupTripTypeFacade().
                    toTripType((String) formInfo.get("type")));
        }
        Logger.getLogger("errorLogger").trace("Exiting from: editTrip");
    }

    /**
     * Method used for validating user input data.
     * @param formInfo form with user data
     * @param request HTTP request
     * @return true if form is well validated, false otherwise
     */
    private boolean validateForm(HashMap < String, Object > formInfo,
            HttpServletRequest request) {
        Logger.getLogger("errorLogger").trace("Entering to: validateForm");
        String message;
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        boolean result = true;
        //<editor-fold default-state="collapsed" desc="Obtaining info from request">

        formInfo.put("title", request.getParameter("title"));
        formInfo.put("type", request.getParameter("type"));
        formInfo.put("description", request.getParameter("description"));
        formInfo.put("distance", request.getParameter("distance"));
        formInfo.put("date", request.getParameter("date"));
        formInfo.put("bike", request.getParameter("bike"));

        //</editor-fold>
       try {
            for (Iterator it = formInfo.keySet().iterator(); it.hasNext();) {
                String currentKey = (String) it.next();
                if (currentKey.equals(new String("pageTitle"))
                        || currentKey.equals(new String("formTitle"))
                        || currentKey.equals("bikes")
                        || currentKey.equals("types")) {
                    continue;
                }
                if (request.getParameter(currentKey) == null
                        || request.getParameter(currentKey).isEmpty()) {
                    formInfo.put(currentKey, null);
                    Logger.getLogger("errorLogger").
                            error("Not all fields filled in new trip: "
                            + currentKey);
                    message = localeProvider.getMessage("error.notAllFilled",
                            null, defaultLocale);
                    throw new MPException(message);
                }
            }
        } catch (Exception exception) {
            formInfo.put("message", exception.getMessage());
            formInfo.put("messColor", ApplicationSettings.getFailColour());
            formInfo.put("action", "new.html");
            result = false;
        }
        Logger.getLogger("errorLogger").trace("Exiting from: validateForm");
        return result;
    }

    /**
     * Method used for creating new Trip object.
     * @param formInfo user data from form
     * @param request HTTP request
     * @param bike Motorcycle object
     */
    private void createTrip(HashMap < String, Object > formInfo,
            HttpServletRequest request, Motorcycle bike) throws ParseException,
            MPException {
        Logger.getLogger("errorLogger").trace("Entering to: createTrip");
        String message;
        Trip newTrip;
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        double doubleDistance = Double.parseDouble(
                (String) formInfo.get("distance"));
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = sdf.parse((String) formInfo.get("date"));

        newTrip = new Trip(newDate, (String)
                formInfo.get("description"), doubleDistance,
                (String) formInfo.get("title"),
                (String) formInfo.get("type"), bike,
                BeanGetter.getUserInfo().getUser());
        BeanGetter.lookupTripFacade().create(newTrip);
        FuzzyDriver.recalculateUserDistance();
        formInfo = new HashMap < String, Object >();
        formInfo.put("title", newTrip.getTitle());
        formInfo.put("type",
                newTrip.getType().getDescription());
        formInfo.put("description", newTrip.getDescription());
        formInfo.put("date", newTrip.getDate());
        formInfo.put("distance", newTrip.getDistance());
        formInfo.put("bike", newTrip.getBike().getId());
        formInfo.put("formTitle", localeProvider.
                getMessage("trips.formTitle.edit", null,
                defaultLocale));
        Logger.getLogger("errorLogger").trace("Exiting from: createTrip");
    }
}
