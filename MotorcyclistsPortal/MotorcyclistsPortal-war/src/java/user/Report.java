/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import ai.fuzzyficators.FishierElementBridgeFuzzyficator;
import ai.fuzzyficators.TripsFuzzyficator;
import entities.FishierElementBridge;
import entities.Motorcycle;
import entities.Trip;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPUtilities;

/**
 *
 * @author kalosh
 */
public class Report {

    /**
     * Method used for showing report of usage for specified Motorcycle.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public final ModelAndView generateReport(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: loc, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object > ();
        // </editor-fold>
        String bikeid = request.getParameter("bike");
        Motorcycle bike = MPUtilities.findBike(bikeid);
        String fishierid = bike.getFishier().getId().toString();

        List < Motorcycle > bikes = MPUtilities.findBikesWFishiers();
        List < Trip > trips = MPUtilities.findTrips();
        List < FishierElementBridge > fishierElements =
                MPUtilities.findFishierElementBridgeByFishier(fishierid);
        List < String > fuzzyPartUsage = null;
        List < String > fuzzyTripDistance = null;

        String fuzzyAverageValue = null;
        try {
            fuzzyPartUsage = new FishierElementBridgeFuzzyficator().
                    processCollection(fishierElements);
            fuzzyTripDistance = new TripsFuzzyficator().
                    processCollection(trips);
            fuzzyAverageValue = MPUtilities.getFuzzyAvgDist().getDescription();
        } catch (Exception exception) {
            formInfo.put("message", localeProvider.getMessage(
                    "ai.computingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            exception.printStackTrace();
        }
        formInfo.put("fuzzyAverageValue", fuzzyAverageValue);
        formInfo.put("bikes", bikes);
        formInfo.put("fuzzyPartUsage", fuzzyPartUsage);
        formInfo.put("fuzzyTripLength", fuzzyTripDistance);
        formInfo.put("bike", bike);
        formInfo.put("fishierElements", fishierElements);
        formInfo.put("pageTitle", localeProvider.getMessage(
                "report.pageTitle", null, defaultLocale));
        return new ModelAndView("secured/report", formInfo);
    }
}