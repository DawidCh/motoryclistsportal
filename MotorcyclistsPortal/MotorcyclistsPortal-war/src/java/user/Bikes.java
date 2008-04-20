/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.Fishier;
import security.DetailedUserInformation;
import entities.Motorcycle;
import java.util.ArrayList;
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
public class Bikes {

    public ModelAndView showList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        DetailedUserInformation userInfo = BeanGetter.getUserInfo();
        List<Motorcycle> bikes = new ArrayList<Motorcycle>(
                BeanGetter.lookupMotorcycleFacade().findByLogin(userInfo.getUsername()));
        formInfo.put("bikes", bikes);
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        return new ModelAndView("bikes/list", formInfo);
    }

    public ModelAndView addBike(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        formInfo.put("formTitle", localeProvider.getMessage("bikes.formTitle.add", null, defaultLocale));
        String form = request.getParameter("form");
        String message;

        //validation
        Motorcycle newBike;
        if (form != null) {
            //<editor-fold default-state="collapsed" desc="Obtaining info from request">
            String nickname = request.getParameter("nickname");
            String manufacturer = request.getParameter("manufacturer");
            String model = request.getParameter("model");
            String year = request.getParameter("year");
            String torque = request.getParameter("torque");
            String power = request.getParameter("power");
            String mileage = request.getParameter("mileage");
            String displacement = request.getParameter("displacement");
            formInfo.put("nickname", nickname);
            formInfo.put("manufacturer", manufacturer);
            formInfo.put("model", model);
            formInfo.put("year", year);
            formInfo.put("torque", torque);
            formInfo.put("power", power);
            formInfo.put("mileage", mileage);
            formInfo.put("displacement", displacement);
            formInfo.put("form", form);
            //</editor-fold>
            for (Iterator it = formInfo.keySet().iterator(); it.hasNext();) {
                String currentKey = (String) it.next();
                if (currentKey.equals(new String("pageTitle")) ||
                        currentKey.equals(new String("formTitle"))) {
                    continue;
                }
                if (request.getParameter(currentKey) == null ||
                        request.getParameter(currentKey).isEmpty()) {
                    MPLogger.severe("Not all fields filled in new bike: " + currentKey);
                    message = localeProvider.getMessage("notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    formInfo.put("action", "new.html");
                    return new ModelAndView("bikes/add", formInfo);
                }
            }
            newBike = new Motorcycle(
                    manufacturer, model, new Integer(year), new Integer(torque),
                    new Integer(power), new Double(mileage), new Integer(displacement),
                    nickname, BeanGetter.getUserInfo().getUser());
            try {
                BeanGetter.lookupMotorcycleFacade().create(newBike);

            } catch (Exception exception) {
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.put("action", "new.html");
                MPLogger.severe("Error wihle persisting in db at Bikes.add: " + exception.getMessage());
                return new ModelAndView("bikes/add", formInfo);
            }
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo = new HashMap<String, Object>();
            formInfo.put("formTitle", localeProvider.getMessage("bikes.formTitle.edit", null, defaultLocale));
            formInfo.put("message", message);
            formInfo.put("nickname", nickname);
            formInfo.put("manufacturer", manufacturer);
            formInfo.put("model", model);
            formInfo.put("year", year);
            formInfo.put("torque", torque);
            formInfo.put("power", power);
            formInfo.put("mileage", mileage);
            formInfo.put("displacement", displacement);
            formInfo.put("action", "edit.html");
            formInfo.put("messColor", DefaultValues.getSuccColor());
            formInfo.put("bike", newBike.getId());
            return new ModelAndView("bikes/add", formInfo);
        }
        formInfo.put("action", "new.html");
        return new ModelAndView("bikes/add", formInfo);
    }
    
    public ModelAndView reassignFishier(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        String message = new String();
        String bikeId = request.getParameter("bike");
        try {
            String fishier = request.getParameter("fishier");
            formInfo.put("fishier", fishier);
            if (bikeId == null || bikeId.isEmpty() || fishier == null || fishier.isEmpty())
                throw new Exception();
            Motorcycle bikeObject = BeanGetter.lookupMotorcycleFacade().find(Integer.parseInt(bikeId));
            formInfo.put("bike", bikeObject);
            Fishier fish = this.findFishier(fishier);
            if(fish == null || !fish.getId().equals(new Integer(fishier)))
                throw new Exception("Fishier not found at Bikes.reaasignFishier");
            bikeObject.setFishier(null);
            BeanGetter.lookupMotorcycleFacade().edit(bikeObject);
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
            formInfo.putAll(this.showList(request, response).getModel());
            return new ModelAndView("bikes/details", formInfo);
        } catch (Exception ex) {
            MPLogger.severe("Error while reassigning fishier to bike in Bikes.reassignFishier");
            message = localeProvider.getMessage("error.otherError", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getFailColor());
            formInfo.putAll(this.showList(request, response).getModel());
            ex.printStackTrace();
            return new ModelAndView("bikes/details", formInfo);
        }
    }

    public ModelAndView editBike(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("formTitle", localeProvider.getMessage("bikes.formTitle.edit", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        Motorcycle bike = null;
        try {
            bike = BeanGetter.lookupMotorcycleFacade().find(new Integer(bikeId));

        } catch (Exception exception) {
            MPLogger.severe("Bike not found at bikes edit: " + bikeId);
            Map map = this.showList(request, response).getModel();
            map.put("message", localeProvider.getMessage("bikes.bikeNotFound", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            exception.printStackTrace();
            return new ModelAndView("bikes/list", map);
        }
        //<editor-fold default-state="collapsed" desc="Obtaining info from request">
        String nickname = bike.getNickname();
        String manufacturer = bike.getManufacturer();
        String model = bike.getModel();
        String year = new Integer(bike.getYear()).toString();
        String torque = new Integer(bike.getTorque()).toString();
        String power = new Integer(bike.getPower()).toString();
        String mileage = new Double(bike.getMileage()).toString();
        String displacement = new Integer(bike.getYear()).toString();
        String form = request.getParameter("form");
        //</editor-fold>
        String message;
        if (form != null) {
            nickname = request.getParameter("nickname");
            manufacturer = request.getParameter("manufacturer");
            model = request.getParameter("model");
            year = request.getParameter("year");
            torque = request.getParameter("torque");
            power = request.getParameter("power");
            mileage = request.getParameter("mileage");
            displacement = request.getParameter("displacement");
            form = request.getParameter("form");
            for (Iterator it = formInfo.keySet().iterator(); it.hasNext();) {
                String currentKey = (String) it.next();
                if (currentKey.equals(new String("pageTitle")) ||
                        currentKey.equals(new String("formTitle"))) {
                    continue;
                }
                if (request.getParameter(currentKey) == null ||
                        request.getParameter(currentKey).isEmpty()) {
                    MPLogger.severe("Not all fields filled in edit bike: " + currentKey);
                    message = localeProvider.getMessage("notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    formInfo.put("action", "edit.html");
                    return new ModelAndView("bikes/add", formInfo);
                }
            }
            if (!nickname.equals(bike.getNickname())) {
                bike.setNickname(nickname);
            }
            if (!manufacturer.equals(bike.getManufacturer())) {
                bike.setManufacturer(manufacturer);
            }
            if (!model.equals(bike.getModel())) {
                bike.setModel(model);
            }
            if (Integer.parseInt(year) != bike.getYear()) {
                bike.setYear(Integer.parseInt(year));
            }
            if (Integer.parseInt(torque) != bike.getTorque()) {
                bike.setTorque(Integer.parseInt(torque));
            }
            if (Integer.parseInt(power) != bike.getPower()) {
                bike.setPower(Integer.parseInt(power));
            }
            if (Integer.parseInt(displacement) != bike.getEnginecapacity()) {
                bike.setEnginecapacity(Integer.parseInt(displacement));
            }
            if (Double.parseDouble(mileage) != bike.getMileage()) {
                bike.setMileage(Double.parseDouble(mileage));
            }

            try {
                BeanGetter.lookupMotorcycleFacade().edit(bike);

            } catch (Exception exception) {
                MPLogger.severe("Error while persisting bike");
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                exception.printStackTrace();
            }
            message = localeProvider.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
        }
        formInfo.put("nickname", nickname);
        formInfo.put("manufacturer", manufacturer);
        formInfo.put("model", model);
        formInfo.put("year", year);
        formInfo.put("torque", torque);
        formInfo.put("power", power);
        formInfo.put("mileage", mileage);
        formInfo.put("displacement", displacement);
        formInfo.put("form", form);
        formInfo.put("action", "edit.html");
        formInfo.put("bike", bike.getId());
        return new ModelAndView("bikes/add", formInfo);
    }

    public ModelAndView deleteBike(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        if (bikeId == null) {
            MPLogger.severe("Null bike id at deleteBike");
            formInfo.put("errorMessage", localeProvider.getMessage("error.otherError", null, defaultLocale));
            return new ModelAndView("unsecured/error", formInfo);
        }
        Map map = null;
        Motorcycle bikeToDel = null;
        try {
            bikeToDel = this.findBike(bikeId);
            BeanGetter.lookupMotorcycleFacade().remove(bikeToDel);
            map = this.showList(request, response).getModel();
        } catch (Exception mPException) {
            map = this.showList(request, response).getModel();
            map.put("message", localeProvider.getMessage("error.errorWhileDeleting", null, defaultLocale));
            map.put("messColor", DefaultValues.getFailColor());
            mPException.printStackTrace();
            return new ModelAndView("/bikes/list", map);
        }
        map.put("message", localeProvider.getMessage("success", null, defaultLocale));
        map.put("messColor", DefaultValues.getSuccColor());
        return new ModelAndView("/bikes/list", map);
    }

    public ModelAndView details(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        String bikeId = request.getParameter("bike");
        Motorcycle bike = null;
        try {
            bike = this.findBike(bikeId);
            if (bike == null) {
                throw new Exception("Bike not found at details");
            }
            formInfo.put("bike", bike);
        } catch (Exception ex) {
            MPLogger.severe("Bike not found");
            formInfo.put("errorMessage", localeProvider.getMessage("bikes.bikeNotFound", null, defaultLocale));
            ex.printStackTrace();
            return new ModelAndView("unsecured/error", formInfo);
        }
        return new ModelAndView("bikes/details", formInfo);
    }

    public ModelAndView assignFishier(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        // </editor-fold>
        formInfo.put("pageTitle", localeProvider.getMessage("bikes.pageTitle", null, defaultLocale));
        String form = request.getParameter("form");
        String message = new String();
        String bike = request.getParameter("bike");
        formInfo.put("bike", bike);
        if (form != null) {
            try {
                String fishier = request.getParameter("fishier");
                formInfo.put("fishier", fishier);
                if (bike == null || bike.isEmpty() || fishier == null || fishier.isEmpty())
                    throw new Exception();
                Motorcycle bikeObject = BeanGetter.lookupMotorcycleFacade().find(Integer.parseInt(bike));
                Fishier fish = this.findFishier(fishier);
                bikeObject.setFishier(fish);
                BeanGetter.lookupMotorcycleFacade().edit(bikeObject);
                message = localeProvider.getMessage("success", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getSuccColor());
                formInfo.putAll(this.showList(request, response).getModel());
                return new ModelAndView("bikes/list", formInfo);
            } catch (Exception ex) {
                MPLogger.severe("Error while assigning fishier to bike in Bikes.assignFishier");
                message = localeProvider.getMessage("error.otherError", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                formInfo.putAll(this.showList(request, response).getModel());
                return new ModelAndView("bikes/list", formInfo);
            }
        } else {
            List<Fishier> fishiers = this.findFishiers();
            formInfo.put("fishiers", fishiers);
        }
        return new ModelAndView("bikes/selectFishier", formInfo);
    }

    private List<Fishier> findFishiers() {
        return BeanGetter.getUserInfo().getFishiers();
    }

    private Motorcycle findBike(String bikeId) throws MPException {
        for (Motorcycle bike : BeanGetter.getUserInfo().getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                return bike;
            }
        }
        throw new MPException("Bike not found at findBike");
    }
    
    private Fishier findFishier(String fishierId) throws MPException {
        for (Fishier fishier : BeanGetter.getUserInfo().getFishiers()) {
            if (fishier.getId().toString().equals(fishierId)) {
                return fishier;
            }
        }
        throw new MPException("Fishier not found at Bikes.findFishier");
    }
}
