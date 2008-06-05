/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.Motorcycle;
import entities.Trip;
import entities.TripType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class Trips {

    public ModelAndView showList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        List<Trip> trips = trips = this.findTrips();
        formInfo.put("trips", trips);
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        return new ModelAndView("trips/list", formInfo);
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
        String message;

        //validation
        Trip newTrip;
        List<TripType> tripTypes = BeanGetter.lookupTripTypeFacade().findAll();
        List<Motorcycle> bikes = BeanGetter.lookupMotorcycleFacade().findByLogin(BeanGetter.getUserInfo().getUsername());
        formInfo.put("types", tripTypes);
        formInfo.put("bikes", bikes);
        if (form != null) {
            //<editor-fold default-state="collapsed" desc="Obtaining info from request">
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            String description = request.getParameter("description");
            String bikeId = request.getParameter("bike");
            String date = request.getParameter("date");
            String distance = request.getParameter("distance");
            formInfo.put("title", title);
            formInfo.put("type", type);
            formInfo.put("description", description);
            formInfo.put("distance", distance);
            formInfo.put("date", date);
            formInfo.put("bike", bikeId);

            formInfo.put("form", form);
            //</editor-fold>
            for (Iterator it = formInfo.keySet().iterator(); it.hasNext();) {
                String currentKey = (String) it.next();
                if (currentKey.equals(new String("pageTitle")) ||
                        currentKey.equals(new String("formTitle")) ||
                        currentKey.equals("bikes") ||
                        currentKey.equals("types")) {
                    continue;
                }
                if (request.getParameter(currentKey) == null ||
                        request.getParameter(currentKey).isEmpty()) {
                    MPLogger.severe("Not all fields filled in new trip: " + currentKey);
                    message = localeProvider.getMessage("notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    formInfo.put("action", "new.html");
                    return new ModelAndView("trips/add", formInfo);
                }
            }
            Motorcycle bike = this.findBike(bikeId);
            if (bike == null) {
                MPLogger.severe("Bike not found at Trips add: " + bikeId);
                message = localeProvider.getMessage("trips.bikeNotFound", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                return new ModelAndView("trips/add", formInfo);
            }
            try {
                double doubleDistance = Double.parseDouble(distance);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date newDate = sdf.parse(date);

                newTrip = new Trip(newDate, description, doubleDistance, title, type, bike, BeanGetter.getUserInfo().getUser());
                BeanGetter.lookupTripFacade().create(newTrip);

            } catch (ParseException ex) {
                formInfo.put("date", null);
                message = localeProvider.getMessage("error.wrongDate", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                return new ModelAndView("trips/add", formInfo);
            } catch (NumberFormatException ex) {
                formInfo.put("distance", null);
                message = localeProvider.getMessage("error.parsingError", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                return new ModelAndView("trips/add", formInfo);
            } catch (Exception exception) {
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                MPLogger.severe("Error wihle persisting in db at Trip.add: " + exception.getMessage());
                return new ModelAndView("trips/add", formInfo);
            }
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo = new HashMap<String, Object>();
            formInfo.put("title", newTrip.getTitle());
            formInfo.put("type", newTrip.getType().getDescription());
            formInfo.put("description", newTrip.getDescription());
            formInfo.put("date", newTrip.getDate());
            formInfo.put("distance", newTrip.getDistance());
            formInfo.put("bike", newTrip.getBike().getId());
            formInfo.put("types", tripTypes);
            formInfo.put("bikes", bikes);
            formInfo.put("formTitle", localeProvider.getMessage("trips.formTitle.edit", null, defaultLocale));
            formInfo.put("message", message);
            formInfo.put("action", "edit.html");
            formInfo.put("messColor", DefaultValues.getSuccColor());
            return new ModelAndView("trips/add", formInfo);
        }
        formInfo.put("action", "new.html");
        return new ModelAndView("trips/add", formInfo);
    }

    public ModelAndView editTrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.getMessage("trips.formTitle.edit", null, defaultLocale));
        String form = request.getParameter("form");
        String tripId = request.getParameter("trip");

        String message;

        //validation
        Trip trip;
        List<TripType> tripTypes = BeanGetter.lookupTripTypeFacade().findAll();
        List<Motorcycle> bikes = BeanGetter.lookupMotorcycleFacade().findByLogin(BeanGetter.getUserInfo().getUsername());
        formInfo.put("types", tripTypes);
        formInfo.put("bikes", bikes);
        try {
            trip = BeanGetter.lookupTripFacade().find(new Integer(tripId));
            formInfo.put("trip", trip.getId());
            formInfo.put("title", trip.getTitle());
            formInfo.put("type", trip.getType().getDescription());
            formInfo.put("description", trip.getDescription());
            formInfo.put("date", trip.getDate());
            formInfo.put("distance", trip.getDistance());
            formInfo.put("bike", trip.getBike().getId());
        } catch (Exception exception) {
            MPLogger.severe("Trip not found at trips edit: " + tripId);
            Map map = this.showList(request, response).getModel();
            map.put("message", localeProvider.getMessage("trips.tripNotFound", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            exception.printStackTrace();
            return new ModelAndView("trips/list", map);
        }
        if (form != null) {
            //<editor-fold default-state="collapsed" desc="Obtaining info from request">
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            String description = request.getParameter("description");
            String bikeId = request.getParameter("bike");
            String date = request.getParameter("date");
            String distance = request.getParameter("distance");
            
            formInfo.put("title", title);
            formInfo.put("type", type);
            formInfo.put("description", description);
            formInfo.put("distance", distance);
            formInfo.put("date", date);
            formInfo.put("bike", bikeId);

            formInfo.put("form", form);
            //</editor-fold>
            for (Iterator it = formInfo.keySet().iterator(); it.hasNext();) {
                String currentKey = (String) it.next();
                if (currentKey.equals(new String("pageTitle")) ||
                        currentKey.equals(new String("formTitle")) ||
                        currentKey.equals("bikes") ||
                        currentKey.equals("types")) {
                    continue;
                }
                if (request.getParameter(currentKey) == null ||
                        request.getParameter(currentKey).isEmpty()) {
                    MPLogger.severe("Not all fields filled in new bike: " + currentKey);
                    message = localeProvider.getMessage("notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    formInfo.put("action", "edit.html");
                    return new ModelAndView("bikes/add", formInfo);
                }
            }

            try {
                double doubleDistance = Double.parseDouble(distance);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date newDate = sdf.parse(date);

                Motorcycle bike = this.findBike(bikeId);
                if (bike == null) {
                    MPLogger.severe("Bike not found at Trips add: " + bikeId);
                    message = localeProvider.getMessage("trips.bikeNotFound", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    formInfo.put("action", "edit.html");
                    return new ModelAndView("trips/add", formInfo);
                }

                if (!trip.getBike().getId().equals(bikeId)) {
                    trip.setBike(bike);
                }
                if (!trip.getDate().equals(newDate)) {
                    trip.setDate(newDate);
                }
                if (!trip.getDescription().equals(description)) {
                    trip.setDescription(description);
                }
                if (trip.getDistance() != doubleDistance) {
                    trip.setDistance(doubleDistance);
                }
                if (!trip.getTitle().equals(title)) {
                    trip.setTitle(title);
                }
                if (!trip.getType().getDescription().equals(type)) {
                    trip.setType(BeanGetter.lookupTripTypeFacade().toTripType(type));
                }
                BeanGetter.lookupTripFacade().edit(trip);
                formInfo.put("title", title);
                formInfo.put("type", type);
                formInfo.put("description", description);
                formInfo.put("bike", bikeId);
                formInfo.put("date", date);
                formInfo.put("distance", distance);
                formInfo.put("bike", bikeId);

            } catch (NumberFormatException ex) {
                formInfo.put("distance", null);
                message = localeProvider.getMessage("error.parsingError", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "edit.html");
                return new ModelAndView("trips/add", formInfo);
            } catch (ParseException ex) {
                formInfo.put("date", null);
                message = localeProvider.getMessage("error.wrongDate", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "edit.html");
                return new ModelAndView("trips/add", formInfo);
            } catch (Exception exception) {
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "edit.html");
                MPLogger.severe("Error wihle persisting in db at Trip.add: " + exception.getMessage());
                return new ModelAndView("trips/add", formInfo);
            }
            message = localeProvider.getMessage("success", null, defaultLocale);

            formInfo.put("message", message);
            formInfo.put("action", "edit.html");
            formInfo.put("messColor", DefaultValues.getSuccColor());
            return new ModelAndView("trips/add", formInfo);
        }
        formInfo.put("action", "edit.html");
        return new ModelAndView("trips/add", formInfo);
    }

    public ModelAndView deleteTrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        String tripId = request.getParameter("trip");
        if (tripId == null) {
            MPLogger.severe("Null trip id at Trips.deleteTrip");
            formInfo.put("errorMessage", localeProvider.getMessage("error.otherError", null, defaultLocale));
            return new ModelAndView("unsecured/error", formInfo);
        }
        Map map = null;
        Trip tripToDel = null;
        try {
            tripToDel = this.findTrip(tripId);
            BeanGetter.lookupTripFacade().remove(tripToDel);
            map = this.showList(request, response).getModel();
        } catch (Exception mPException) {
            map = this.showList(request, response).getModel();
            map.put("message", localeProvider.getMessage("error.errorWhileDeleting", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            mPException.printStackTrace();
            return new ModelAndView("/trips/list", map);
        }
        map.put("message", localeProvider.getMessage("success", null, defaultLocale));
        map.put("messColor", DefaultValues.getSuccColor());
        return new ModelAndView("/trips/list", map);
    }

    public ModelAndView details(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("trips.pageTitle", null, defaultLocale));
        String tripId = request.getParameter("trip");
        Trip trip = null;
        try {
            trip = this.findTrip(tripId);
            if (trip == null) {
                throw new Exception("Trip not found at Trip.details");
            }
            formInfo.put("trip", trip);
        } catch (Exception ex) {
            MPLogger.severe("Trip not found");
            formInfo.put("errorMessage", localeProvider.getMessage("trips.tripNotFound", null, defaultLocale));
            ex.printStackTrace();
            return new ModelAndView(this.showList(request, response).getView(), formInfo);
        }
        return new ModelAndView("trips/details", formInfo);
    }

    private Motorcycle findBike(String bikeId) throws MPException {
        for (Motorcycle bike : BeanGetter.getUserInfo().getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                return bike;
            }
        }
        throw new MPException("Trip not found at Trips.findBike");
    }

    private Trip findTrip(String tripId) throws MPException {
        for (Trip trip : BeanGetter.getUserInfo().getTripCollection()) {
            if (trip.getId().toString().equals(tripId)) {
                return trip;
            }
        }
        throw new MPException("Trip not found at Trips.findTrip");
    }

    private List<Trip> findTrips() {
        return BeanGetter.getUserInfo().getTripCollection();
    }
}
