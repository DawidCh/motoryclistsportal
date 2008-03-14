/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import utils.MPLogger;
import entities.LoginData;
import entities.Privileges;
import entities.User;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPException;

/**
 *
 * @author kalosh
 */
public class Register implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        formInfo.put("pageTitle", localeProvider.getMessage("register.pageTitle", null, defaultLocale));
        // </editor-fold>
        if (null != request.getParameter("form")) {
            //data verification
            String message = new String();
            formInfo.put("newLogin", request.getParameter("newLogin"));
            formInfo.put("password", request.getParameter("password"));
            formInfo.put("passwordAgain", request.getParameter("passwordAgain"));
            formInfo.put("name", request.getParameter("name"));
            formInfo.put("surname", request.getParameter("surname"));
            formInfo.put("city", request.getParameter("city"));
            formInfo.put("birthdate", request.getParameter("birthdate"));
            formInfo.put("gender", request.getParameter("gender"));
            formInfo.put("form", request.getParameter("form"));

            String[] keyList = formInfo.keySet().toArray(new String[formInfo.keySet().size()]);
            for (int i = 0; i < keyList.length; i++) {
                if (null == formInfo.get(keyList[i]) || formInfo.get(keyList[i]).equals(new String(""))) {
                    message = localeProvider.getMessage("register.notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    return new ModelAndView("unsecured/register", formInfo);
                }
            }

            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("d M y").parse((String)formInfo.get("birthdate"));
            } catch (ParseException ex) {
                message = localeProvider.getMessage("register.wrongDate", null, defaultLocale);
                formInfo.put("message", message);
                MPLogger.severe("Wrong date format in Register from " + formInfo.get("birthdate"));
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("unsecured/register", formInfo);
            }

            int passCheckingRes = this.checkPassword((String)formInfo.get("password"), (String)formInfo.get("passwordAgain"));
            if (passCheckingRes == 1) {
                message = localeProvider.getMessage("register.differentPass", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("password", null);
                formInfo.put("passwordAgain", null);
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("unsecured/register", formInfo);
            } else if (passCheckingRes == 2) {
                message = localeProvider.getMessage("register.wrongLength", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("password", null);
                formInfo.put("passwordAgain", null);
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("unsecured/register", formInfo);
            }
            //end of verification


            Privileges choosenPriv = BeanGetter.lookupPrivilegesFacade().findByDesc("userusers");
            User user = new User((String)formInfo.get("newLogin"), (String)formInfo.get("name"),
                    (String)formInfo.get("surname"), (String)formInfo.get("city"),
                    (String)formInfo.get("gender"), birthdate,defaultLocale);
            LoginData loginData = null;
            try {
                loginData = new LoginData((String)formInfo.get("newLogin"), this.computeSha((String)formInfo.get("password")));
            } catch (Exception exception) {
                MPLogger.severe("Error while creating LoginData object");
                formInfo.put("message", localeProvider.getMessage("register.shaerror", null, defaultLocale));
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("unsecured/register", formInfo);
            }

            user.setLoginData(loginData);
            user.setLocale(defaultLocale);
            loginData.setUser(user);
            loginData.setPrivileges(choosenPriv);

            try {
                BeanGetter.lookupUserBean().createUser(user, loginData);
            } catch (Exception exception) {
                String excMess = exception.getMessage();
                MPLogger.severe("Error while adding to database in Register: " + excMess);
                message = localeProvider.getMessage("register.addtobase", null, defaultLocale);
                if (excMess.equals(MPException.LOGIN_EXISTS)) {
                    message += ": " + localeProvider.getMessage("register.loginExists", null, defaultLocale);
                } else {
                    message += ": " + localeProvider.getMessage("otherError", null, defaultLocale);
                }
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("unsecured/register", formInfo);
            }

            //
            message = localeProvider.getMessage("success", null, defaultLocale);
            message += ", " + localeProvider.getMessage("register.lognowin", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
            return new ModelAndView("unsecured/register", formInfo);
        }
        return new ModelAndView("unsecured/register", formInfo);
    }

    private String computeSha(String messageToDigest) throws Exception {
        String result = new String();
        MessageDigest md = MessageDigest.getInstance("sha");
        md.reset();
        md.update(messageToDigest.getBytes());
        for (byte b : md.digest()) {
            result += Integer.toHexString(b & 0xff);
        }
        return result;
    }

    private int checkPassword(String pass, String secPass) {
        if (!pass.equals(secPass)) {
            return 1;
        } else if (pass.length() > DefaultValues.getPassLength()[1] || pass.length() < DefaultValues.getPassLength()[0]) {
            return 2;
        } else {
            return 0;
        }
    }
}
