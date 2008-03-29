/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

/**
 *
 * @author kalosh
 */
public class Trips {
    /*
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
                        request.getParameter(currentKey).equals(new String(""))) {
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
                        request.getParameter(currentKey).equals(new String(""))) {
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
                MPLogger.severe("Error while persisting bikee");
                message = localeProvider.getMessage("error.errorWhileAdding", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
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
        DetailedUserInformation user = BeanGetter.getUserInfo();
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
            return new ModelAndView("unsecured/error", formInfo);
        }
        return new ModelAndView("bikes/details", formInfo);
    }

    private Motorcycle findTrip(String bikeId) throws MPException {
        for (Motorcycle bike : BeanGetter.getUserInfo().getMotorcycleCollection()) {
            if (bike.getId().toString().equals(bikeId)) {
                return bike;
            }
        }
        throw new MPException("Bike not found at findBike");
    }*/
}
