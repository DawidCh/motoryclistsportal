/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import ai.FuzzyDriver;
import entities.FishierElementBridge;
import entities.FishierElementBridge;
import entities.Motorcycle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPUtilities;

/**
 *
 * @author kalosh
 */
public class Report implements Controller{

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: loc, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        String bikeid = request.getParameter("bike");
        Motorcycle bike = MPUtilities.findBike(bikeid);
        String fishierid = bike.getFishier().getId().toString();
        List<Motorcycle> bikes = this.findBikesWFishiers();
        
        List<FishierElementBridge> fishierElements = this.findFishierElementBridgeByFishier(fishierid);
        List<String> fuzzyResult = null;
        try {
            fuzzyResult = FuzzyDriver.getInstance().generateResult(fishierElements);

        } catch (Exception exception) {
            formInfo.put("message", localeProvider.getMessage("ai.computingError", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
        }

        
        formInfo.put("bikes", bikes);
        formInfo.put("fuzzyResult", fuzzyResult);
        formInfo.put("bike", bike);
        formInfo.put("fishierElements", fishierElements);
        formInfo.put("pageTitle", localeProvider.getMessage("report.pageTitle", null, defaultLocale));
        return new ModelAndView("secured/report", formInfo);
    }

    private List<FishierElementBridge> findFishierElementBridgeByFishier(String fishierId) {
        return BeanGetter.lookupFishierElementBridgeFacade().findAllByFishier(fishierId);
    }
    
    private List<Motorcycle> findBikesWFishiers(){
        List<Motorcycle> result = new ArrayList<Motorcycle>();
        List<Motorcycle> bikes = BeanGetter.lookupMotorcycleFacade().findByLogin(BeanGetter.getUserInfo().getUsername());
        for (Iterator<Motorcycle> it = bikes.iterator(); it.hasNext();) {
            Motorcycle motorcycle = it.next();
            if(motorcycle.getFishier() != null)
                result.add(motorcycle);
        }
        return result;
    }
}