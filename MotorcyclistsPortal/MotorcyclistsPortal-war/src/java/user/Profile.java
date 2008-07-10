/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.LoginData;
import entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPUtilities;

/**
 *
 * @author kalosh
 */
public class Profile extends AbstractController {

    /**
     * Method used for showing list of fishiers which are owned by logged user.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    @Override
    protected ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: handleRequestInternal");
        ModelAndView result = null;
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        LoginData loginData;
        User user;
        try {
            loginData = BeanGetter.getUserInfo().getUser().getLoginData();
            user = BeanGetter.getUserInfo().getUser();

        } catch (Exception exception) {
            formInfo.put("message", localeProvider.getMessage(
                    "profile.errorRecivingData", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColour());
            Logger.getLogger("E").error("Error while reciving data from database"
                    + "at Profile: " + exception.getMessage());
            return new ModelAndView("user/profile", formInfo);
        }

        formInfo.put("defaultLocale", defaultLocale);
        formInfo.put("pageTitle", localeProvider.getMessage("profile.pageTitle",
                null, defaultLocale));

        formInfo.put("name", user.getName());
        formInfo.put("surname", user.getSurname());
        formInfo.put("city", user.getCity());
        formInfo.put("gender", user.getGender());
        formInfo.put("mileageType", user.getMileageType());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        formInfo.put("birthdate", sdf.format(user.getBirthdate()));
        // </editor-fold>
        if (null != request.getParameter("form")) {
            Logger.getLogger("E").error(request.getParameter("city"));
            //<editor-fold defaultstate="collapsed" desc="data verification">
            String message = new String();
            String password = request.getParameter("password");
            String passwordAgain = request.getParameter("passwordAgain");
            formInfo.put("name", request.getParameter("name"));
            formInfo.put("surname", request.getParameter("surname"));
            formInfo.put("city", request.getParameter("city"));
            formInfo.put("birthdate", request.getParameter("birthdate"));
            formInfo.put("gender", request.getParameter("gender"));
            formInfo.put("form", request.getParameter("form"));
            formInfo.put("mileageType", request.getParameter("mileageType"));


            String[] keyList = formInfo.keySet().toArray(
                    new String[formInfo.keySet().size()]);
            for (int i = 0; i < keyList.length; i++) {
                if (null == formInfo.get(keyList[i])
                        || formInfo.get(keyList[i]).equals(new String(""))) {
                    message = localeProvider.getMessage("profile.error.notAllFilled",
                            null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    return new ModelAndView("user/profile", formInfo);
                }
            }

            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("dd MM yyyy").
                        parse((String) formInfo.get("birthdate"));
            } catch (ParseException parseException) {
                message = localeProvider.getMessage("profile.wrongDate", null,
                        defaultLocale);
                formInfo.put("message", message);
                Logger.getLogger("E").
                        error("Wrong date format in Register from "
                        + formInfo.get("birthdate"));
                formInfo.put("messColor", DefaultValues.getFailColour());
                return new ModelAndView("user/profile", formInfo);
            }

            if (!password.equals(new String(""))
                    || !passwordAgain.equals(new String(""))) {
                int passCheckingRes =
                        MPUtilities.checkPassword(password, passwordAgain);
                if (passCheckingRes == 1) {
                    message = localeProvider.getMessage("profile.differentPass",
                            null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    return new ModelAndView("user/profile", formInfo);
                } else if (passCheckingRes == 2) {
                    message = localeProvider.getMessage("profile.wrongLength",
                            null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColour());
                    return new ModelAndView("user/profile", formInfo);
                }
            }
            //</editor-fold>

            if (!user.getName().equals((String) formInfo.get("name"))) {
                user.setName((String) formInfo.get("name"));
            }
            if (!user.getMileageType().equals((String)
                    formInfo.get("mileageType"))) {
                user.setMileageType((String) formInfo.get("mileageType"));
            }
            if (!user.getName().equals((String) formInfo.get("surname"))) {
                user.setSurname((String) formInfo.get("surname"));
            }
            if (!user.getName().equals((String) formInfo.get("city"))) {
                user.setCity((String) formInfo.get("city"));
            }
            if (!user.getBirthdate().equals(birthdate)) {
                user.setBirthdate(birthdate);
            }
            if (!user.getName().equals((String) formInfo.get("gender"))) {
                user.setGender((String) formInfo.get("gender"));
            }
            if (!password.equals(loginData.getPassword())
                    && !password.equals(new String(""))) {
                //todo: zaszyfrować hasło wprowadzane do bazy
                loginData.setPassword(password);
            }

            try {
                BeanGetter.lookupUserFacade().edit(user);
                message = localeProvider.
                        getMessage("success", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getSuccColour());
                result = new ModelAndView("user/profile", formInfo);
            } catch (Exception exception) {
                String excMess = exception.getMessage();
                Logger.getLogger("E").error("Error while setting data to"
                        + "database in Profile: " + excMess);
                message = localeProvider.getMessage("profile.addtobase",
                        null, defaultLocale) + localeProvider.
                        getMessage("otherError", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColour());
                result = new ModelAndView("user/profile", formInfo);
            }
        }
        if (result == null) {
            result = new ModelAndView("user/profile", formInfo);
        }
        Logger.getLogger("E").trace("Exiting from: handleRequestInternal");
        return result;
    }
}
