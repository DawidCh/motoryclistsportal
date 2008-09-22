/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation.controllers;

import entities.LoginData;
import entities.Privileges;
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
import org.springframework.web.servlet.mvc.Controller;
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
public class Register implements Controller {

    /**
     * Method used for handling register page.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: handleRequest");
        ModelAndView result = null;
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        formInfo.put("pageTitle", localeProvider.getMessage("register.pageTitle", null, defaultLocale));
        // </editor-fold>
        String message = "";
        if (null != request.getParameter("form")) {
            try {
                this.verifyForm(formInfo, request);

                this.createUserObject(formInfo, request);

                message = localeProvider.getMessage("success", null,
                        defaultLocale);
                message += ", " + localeProvider.getMessage("register.lognowin",
                        null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", ApplicationSettings.getSuccColour());
            } catch (MPException mPException) {
                Logger.getLogger("E").error("Error while registering user.");
            }
        }
        result = new ModelAndView("unsecured/register", formInfo);
        Logger.getLogger("E").trace("Exiting from: handleRequest");
        return result;
    }

    /**
     * Method used for verify data which user put in the form on page.
     * @param formInfo data from page (form)
     * @param request HTTP request object
     * @return user object
     * @throws utils.MPException
     */
    private boolean verifyForm(HashMap<String, Object> formInfo,
            final HttpServletRequest request) throws MPException {
        Logger.getLogger("E").trace("Entering to: verifyForm");
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        Date birthdate = null;
        String message = "";
        boolean result = true;
        formInfo.put("newLogin", request.getParameter("newLogin"));
        formInfo.put("password", request.getParameter("password"));
        formInfo.put("passwordAgain",
                request.getParameter("passwordAgain"));
        formInfo.put("name", request.getParameter("name"));
        formInfo.put("surname", request.getParameter("surname"));
        formInfo.put("city", request.getParameter("city"));
        formInfo.put("birthdate", request.getParameter("birthdate"));
        formInfo.put("gender", request.getParameter("gender"));
        formInfo.put("form", request.getParameter("form"));
        formInfo.put("mileageType", request.getParameter("mileageType"));

        String[] keyList = formInfo.keySet().
                toArray(new String[formInfo.keySet().size()]);
        for (int i = 0; i < keyList.length; i++) {
            if (null == formInfo.get(keyList[i])
                    || formInfo.get(keyList[i]).equals(new String(""))) {
                message = localeProvider.getMessage("error.notAllFilled",
                        null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", ApplicationSettings.getFailColour());
                throw new MPException("Not all fields are filled.");
            }
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d M y");
            birthdate = sdf.
                    parse((String) formInfo.get("birthdate"));
        } catch (ParseException ex) {
            message = localeProvider.getMessage("error.wrongDate",
                    null, defaultLocale);
            formInfo.put("message", message);
            Logger.getLogger("E").
                    error("Wrong date format in Register from "
                    + formInfo.get("birthdate"));
            formInfo.put("messColor", ApplicationSettings.getFailColour());
            throw new MPException("Wrong date format.");
        }

        int passCheckingRes = MPUtilities.checkPassword(
                (String) formInfo.get("password"),
                (String) formInfo.get("passwordAgain"));
        if (passCheckingRes == 1) {
            message = localeProvider.getMessage("register.differentPass",
                    null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("password", null);
            formInfo.put("passwordAgain", null);
            formInfo.put("messColor", ApplicationSettings.getFailColour());
            throw new MPException("Passwords are not equal.");
        } else if (passCheckingRes == 2) {
            message = localeProvider.getMessage("register.wrongLength",
                    null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("password", null);
            formInfo.put("passwordAgain", null);
            formInfo.put("messColor", ApplicationSettings.getFailColour());
            throw new MPException("Wrong password length.");
        }
        Logger.getLogger("E").trace("Exiting from: verifyForm");
        return result;
    }

    /**
     * Method for crating user object using form data.
     * @param formInfo data from page (form)
     * @param request HTTP request object
     * @throws utils.MPException
     */
    private void createUserObject(HashMap<String, Object> formInfo,
            final HttpServletRequest request)
            throws MPException {
        Logger.getLogger("E").trace("Entering to: createUserObject");
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        String message = "";
        SimpleDateFormat sdf = new SimpleDateFormat("d M y");
        //todo:zaszyfrować hasło

        LoginData loginData = null;
        try {
            loginData = new LoginData((String) formInfo.get("newLogin"),
                    (String) formInfo.get("password"));
        } catch (Exception exception) {
            Logger.getLogger("E").
                    error("Error while creating LoginData object");
            formInfo.put("message", localeProvider.getMessage(
                    "register.shaerror", null, defaultLocale));
            formInfo.put("messColor", ApplicationSettings.getFailColour());
            throw new MPException("Error while creating login data");
        }
        Privileges choosenPriv = BeanGetter.lookupPrivilegesFacade().
                findByDesc("ROLE_USER");
        User user = null;
        try {
            user = new User((String) formInfo.get("newLogin"),
                (String) formInfo.get("name"),
                (String) formInfo.get("surname"),
                (String) formInfo.get("city"),
                (String) formInfo.get("gender"),
                sdf.parse((String) formInfo.get("birthdate")), defaultLocale,
                (String) formInfo.get("mileageType"));
            user.setLoginData(loginData);
            user.setLocale(defaultLocale);
            loginData.setUser(user);
            loginData.setPrivileges(choosenPriv);
            BeanGetter.lookupUserFacade().create(user);
        } catch (Exception exception) {
            String excMess = exception.getMessage();
            Logger.getLogger("E").error("Error while adding to database "
                    + "in Register: " + excMess);
            message = localeProvider.getMessage("register.addtobase", null,
                    defaultLocale);
            if (excMess.equals(MPException.LOGIN_EXISTS)) {
                message += ": " + localeProvider.
                        getMessage("register.loginExists", null,
                        defaultLocale);
            } else {
                message += ": " + localeProvider.
                        getMessage("error.otherError", null, defaultLocale);
            }
            formInfo.put("message", message);
            formInfo.put("messColor", ApplicationSettings.getFailColour());
            throw new MPException("Error while creating user.");
        }
        Logger.getLogger("E").trace("Exiting from: createUserObject");
    }
}
