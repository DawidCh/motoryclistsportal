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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import user.UserSession;
import utils.BeanGetter;
import utils.DefaultValues;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Register implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(null != request.getParameter("form"))
        {
            //data verification
            LocaleProvider loc = (LocaleProvider)BeanGetter.getScopedBean("localeProvider", request);
            UserSession userSession = (UserSession) BeanGetter.getScopedBean("userSession", request);
            Locale defaultLocale = userSession.getLanguage();
            String message = new String();
            HashMap<String, String> formInfo = new HashMap<String, String>();
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
                if(null == formInfo.get(keyList[i]) || formInfo.get(keyList[i]).equals(new String(""))){
                    message = loc.getMessage("register.notAllFilled", null, defaultLocale);
                    formInfo.put("message", message);
                    return new ModelAndView("unsecured/register", formInfo);
                }
            }
            
            Date birthdate = null;
            try{
                birthdate = new SimpleDateFormat("d M y").parse(formInfo.get("birthdate"));
            }catch(ParseException ex){
                message = loc.getMessage("register.wrongDate", null, defaultLocale);
                formInfo.put("message", message);
                MPLogger.severe("Wrong date format in Register from "+formInfo.get("birthdate"));
                return new ModelAndView("unsecured/register", formInfo);
            }
            
            int passCheckingRes = this.checkPassword(formInfo.get("password"), formInfo.get("passwordAgain"));
            if(passCheckingRes == 1)
            {
                message = loc.getMessage("register.differentPass", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("password", null);
                formInfo.put("passwordAgain", null);
                return new ModelAndView("unsecured/register", formInfo);
            }
            else if(passCheckingRes == 2)
            {
                message = loc.getMessage("register.wrongLength", null, defaultLocale);
                formInfo.put("message", message);
                formInfo.put("password", null);
                formInfo.put("passwordAgain", null);
                return new ModelAndView("unsecured/register", formInfo);
            }
            //end of verification
            
            
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            Privileges choosenPriv = BeanGetter.lookupPrivilegesFacade().findByDesc("userusers");
            User user = new User(formInfo.get("newLogin"), formInfo.get("name"),
            formInfo.get("surname"), formInfo.get("city"), formInfo.get("gender"), birthdate);
            LoginData loginData = null;
            try {
                loginData = new LoginData(formInfo.get("newLogin"), this.computeSha(formInfo.get("password")), now);
            } catch (Exception exception) {
                MPLogger.severe("Error while creating LoginData object");
                formInfo.put("message", loc.getMessage("register.shaerror", null, defaultLocale));
                return new ModelAndView("unsecured/register", formInfo);
            }
            
            user.setLoginData(loginData);
            loginData.setUser(user);
            loginData.setPrivileges(choosenPriv);
                
            try {
                BeanGetter.lookupUserBean().createUser(user, loginData);
            } catch (Exception exception) {
                MPLogger.severe("Error while adding to database in Register");
                formInfo.put("message", loc.getMessage("register.addtobase", null, defaultLocale)+": "+
                        exception.getMessage());
                return new ModelAndView("unsecured/register", formInfo);
            }

            //
            message = loc.getMessage("success", null, defaultLocale);
            message += ", "+loc.getMessage("register.lognowin", null, defaultLocale);
            formInfo.put("message", message);
            return new ModelAndView("unsecured/register", formInfo);
        }
        return new ModelAndView("unsecured/register");
    }
    
    private String computeSha(String messageToDigest) throws Exception
    {
        String result = new String();
        MessageDigest md = MessageDigest.getInstance("sha");
        md.reset();
        md.update(messageToDigest.getBytes());
        for(byte b:md.digest())
            result += Integer.toHexString(b & 0xff);
        return result;
    }

    private int checkPassword(String pass, String secPass) {
        if(!pass.equals(secPass))
            return 1;
        else if(pass.length() > DefaultValues.getPassLength()[1] || pass.length() < DefaultValues.getPassLength()[0])
            return 2;
        else
            return 0;
    }
}
