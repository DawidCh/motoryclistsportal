/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import ai.FuzzyDriver;
import fuzzyelements.Fuzzyficable;
import entities.Motorcycle;
import fuzzyelements.FuzzyValue;
import java.util.ArrayList;
import java.util.HashMap;
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
        Logger.getLogger("E").trace("Entering to: generateReport");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: loc, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap < String, Object > formInfo = new HashMap < String, Object > ();
        // </editor-fold>
        String bikeid = request.getParameter("bike");
        Motorcycle bike = MPUtilities.findBike(bikeid);
        String fishierid = bike.getFishier().getId().toString();

        FuzzyDriver replacementAdvisor = new FuzzyDriver();
        List < Motorcycle > bikes = MPUtilities.findBikesWFishiers();
        List < Fuzzyficable > trips =
                new ArrayList < Fuzzyficable > (MPUtilities.findTrips());
        List < Fuzzyficable > fishierElements =
                new ArrayList < Fuzzyficable > (
                MPUtilities.findFishierElementBridgeByFishier(fishierid));
        List < FuzzyValue > fuzzyPartUsage = null;
        List < FuzzyValue > fuzzyTripDistance = null;
        List < FuzzyValue > fuzzyReplaceAdvise = null;

        String fuzzyAverageValue = null;
        ModelAndView result = null;
        try {
            fuzzyPartUsage = replacementAdvisor.
                    processFishierElementBridgeCollection(fishierElements);
            fuzzyTripDistance = replacementAdvisor.
                    processTripCollection(trips);
            fuzzyReplaceAdvise = replacementAdvisor.processAdvision();
            fuzzyAverageValue = FuzzyDriver.getFuzzyAvgDist().getDescription();
            result = new ModelAndView("secured/report", formInfo);
        } catch (MPException mpException) {
            formInfo.put("errorMessage", localeProvider.
                    getMessage(mpException.getMessage(), null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            Logger.getLogger("E").debug("Empty list given");
        } catch (Exception exception) {
            formInfo.put("message", localeProvider.getMessage(
                    "ai.computingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            Logger.getLogger("E").error(exception);
        } finally {
            if (result == null) {
                result = new ModelAndView("unsecured/error", formInfo);
            }
        }
        formInfo.put("fuzzyReplaceAdvise", fuzzyReplaceAdvise);
        formInfo.put("fuzzyAverageValue", fuzzyAverageValue);
        formInfo.put("bikes", bikes);
        formInfo.put("fuzzyPartUsage", fuzzyPartUsage);
        formInfo.put("fuzzyTripLength", fuzzyTripDistance);
        formInfo.put("bike", bike);
        formInfo.put("fishierElements", fishierElements);
        formInfo.put("pageTitle", localeProvider.getMessage(
                "report.pageTitle", null, defaultLocale));
        Logger.getLogger("E").trace("Exiting from: generateReport");
        return result;
    }
}