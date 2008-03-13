/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.LoginData;
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
import utils.ApplicationSettings;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class Profile implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String, Object> formInfo = new HashMap<String, Object>();
        LocaleProvider loc = (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
        Locale defaultLocale = RequestContextUtils.getLocale(request);
        LoginData loginData;
        User user;
        try {
            loginData = BeanGetter.lookupLoginDataFacade().find(request.getUserPrincipal().getName());
            user = BeanGetter.lookupUserFacade().find(request.getUserPrincipal().getName());

        } catch (Exception exception) {
            formInfo.put("message", loc.getMessage("profile.errorRecivingData", null, defaultLocale));
            formInfo.put("messColor", DefaultValues.getFailColor());
            MPLogger.severe("Error while reciving data from database at Profile: "+exception.getMessage());
            return new ModelAndView("user/profile", formInfo);
        }
        
        formInfo.put("login", request.getUserPrincipal().getName());
        formInfo.put("failColor", DefaultValues.getFailColor());
        formInfo.put("succColor", DefaultValues.getSuccColor());
        formInfo.put("pageTitle", loc.getMessage("profile.pageTitle", null, defaultLocale));
        formInfo.put("languages",
                ((ApplicationSettings)BeanGetter.getScopedBean("settings", request)).getAvailableLanguages(request));

        formInfo.put("name", user.getName());
        formInfo.put("surname", user.getSurname());
        formInfo.put("city", user.getCity());
        formInfo.put("gender", user.getGender());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        formInfo.put("birthdate", sdf.format(user.getBirthdate()));

        if (null != request.getParameter("form")) {
            MPLogger.severe(request.getParameter("city"));
            //data verification
            String message = new String();
            String password = request.getParameter("password");
            String passwordAgain = request.getParameter("passwordAgain");
            formInfo.put("name", request.getParameter("name"));
            formInfo.put("surname", request.getParameter("surname"));
            formInfo.put("city", request.getParameter("city"));
            formInfo.put("birthdate", request.getParameter("birthdate"));
            formInfo.put("gender", request.getParameter("gender"));
            formInfo.put("form", request.getParameter("form"));


            String[] keyList = formInfo.keySet().toArray(new String[formInfo.keySet().size()]);
            for (int i = 0; i < keyList.length; i++) {
                if (null == formInfo.get(keyList[i]) || formInfo.get(keyList[i]).equals(new String(""))) {
                    message = loc.getMessage("profile.notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    return new ModelAndView("user/profile", formInfo);
                }
            }

            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("dd MM yyyy").parse((String)formInfo.get("birthdate"));
            } catch (ParseException ex) {
                message = loc.getMessage("profile.wrongDate", null, defaultLocale);
                formInfo.put("message", message);
                MPLogger.severe("Wrong date format in Register from " + formInfo.get("birthdate"));
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("user/profile", formInfo);
            }

            if (!password.equals(new String("")) || !passwordAgain.equals(new String(""))) {
                int passCheckingRes = this.checkPassword(password, passwordAgain);
                if (passCheckingRes == 1) {
                    message = loc.getMessage("profile.differentPass", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    return new ModelAndView("user/profile", formInfo);
                } else if (passCheckingRes == 2) {
                    message = loc.getMessage("profile.wrongLength", null, defaultLocale);
                    formInfo.put("message", message);
                    formInfo.put("messColor", DefaultValues.getFailColor());
                    return new ModelAndView("user/profile", formInfo);
                }
            }
            //end of verification

            if (!user.getName().equals((String)formInfo.get("name"))) {
                user.setName((String)formInfo.get("name"));
            }
            if (!user.getName().equals((String)formInfo.get("surname"))) {
                user.setSurname((String)formInfo.get("surname"));
            }
            if (!user.getName().equals((String)formInfo.get("city"))) {
                user.setCity((String)formInfo.get("city"));
            }
            if (!user.getBirthdate().equals(birthdate)) {
                user.setBirthdate(birthdate);
            }
            if (!user.getName().equals((String)formInfo.get("gender"))) {
                user.setGender((String)formInfo.get("gender"));
            }
            if (!this.computeSha(password).equals(loginData.getPassword()) &&
                    !password.equals(new String(""))) {
                loginData.setPassword(this.computeSha(password));
            }

            try {
                BeanGetter.lookupUserBean().editUser(user, loginData);
            } catch (Exception exception) {
                String excMess = exception.getMessage();
                MPLogger.severe("Error while setting data to database in Profile: " + excMess);
                message = loc.getMessage("profile.addtobase", null, defaultLocale) + loc.getMessage("otherError", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("messColor", DefaultValues.getFailColor());
                return new ModelAndView("user/profile", formInfo);
            }

            //
            message = loc.getMessage("success", null, defaultLocale);
            formInfo.put("message", message);
            formInfo.put("messColor", DefaultValues.getSuccColor());
            return new ModelAndView("user/profile", formInfo);
        }
        return new ModelAndView("user/profile", formInfo);
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
