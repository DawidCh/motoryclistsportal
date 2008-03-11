/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.LoginData;
import entities.Privileges;
import entities.User;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import user.UserSession;
import utils.BeanGetter;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Register implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(null != request.getParameter("form"))
        {
            String login = request.getParameter("login");
            String pass = request.getParameter("password");
            String passAgain = request.getParameter("passwordAgain");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String city = request.getParameter("city");
            String birthdate = request.getParameter("birthdate");
            String gender = request.getParameter("gender");
            MPLogger.severe(login+" "+pass+" "+passAgain+" "+name+" "+surname+" "+city+" "+birthdate+" "+gender);
            
            //rejestracja
            
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            String date[] = birthdate.split("-");
            cal.set(new Integer(date[0]), new Integer(date[1]), new Integer(date[2]));
            
            Privileges choosenPriv = BeanGetter.lookupPrivilegesFacade().findByDesc("userusers");
            
            User user = new User(login, name, surname, city, gender, cal.getTime());
            LoginData loginData = new LoginData(login, pass, now);
            
            user.setLoginData(loginData);
            loginData.setUser(user);
            loginData.setPrivileges(choosenPriv);
            
            BeanGetter.lookupUserBean().createUser(user, loginData);
            //
            LocaleProvider loc = (LocaleProvider)BeanGetter.getScopedBean("localeProvider", request);
            UserSession userSession = (UserSession) BeanGetter.getScopedBean("userSession", request);
            String message = loc.getMessage("success", null, userSession.getLanguage());
            message += ", "+loc.getMessage("register.lognowin", null, userSession.getLanguage());
            return new ModelAndView("unsecured/register", "message", message);
        }
        return new ModelAndView("unsecured/register");
    }
}
