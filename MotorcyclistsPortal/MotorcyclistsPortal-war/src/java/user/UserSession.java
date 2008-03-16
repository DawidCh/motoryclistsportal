/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import entities.LoginData;
import entities.User;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import utils.BeanGetter;
import utils.LocaleProvider;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class UserSession {
    
    private User user;
    private LoginData loginData;

    public UserSession() {
    }

    public User getUser() {
        return user;
    }
    
    public void setSessionValues(HttpServletRequest request, HttpServletResponse response)
    {
        this.user = BeanGetter.lookupUserFacade().find(request.getUserPrincipal().getName());
        this.loginData = BeanGetter.lookupLoginDataFacade().find(this.user.getLogin());
        SessionLocaleResolver slr = BeanGetter.getSessionLocaleResolver(request);
        slr.setLocale(request, response, this.user.getLocale());
    }
    
    public void refreshUserFromDB()
    {
        this.user = BeanGetter.lookupUserFacade().find(this.user.getLogin());
        this.loginData = BeanGetter.lookupLoginDataFacade().find(this.user.getLogin());
    }
    public void setLanguageInDB(HttpServletRequest request, Locale locale) throws MPException
    {
        try {
            this.user.setLocale(locale);
            BeanGetter.lookupUserBean().editUser(this.user);
        } catch (MPException ex) {
            MPLogger.severe("Error while persisting language at UserSession");
            LocaleProvider loc = BeanGetter.getLocaleProvider(request);
            throw new MPException(loc.getMessage("session.errorWhilePersist", null, request.getLocale()));
        } catch (NullPointerException ex)
        {
            MPLogger.severe("Error while persisting language at UserSession because user is not in the session");
        }
    }
}
