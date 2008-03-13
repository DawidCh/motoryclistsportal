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
    
    private Locale favouriteLoc;
    private String login;
    private User user;
    private LoginData loginData;

    public UserSession() {
    }
    
    public void setValues(HttpServletRequest request, HttpServletResponse response)
    {
        this.login = request.getUserPrincipal().getName();
        this.user = BeanGetter.lookupUserFacade().find(this.login);
        this.loginData = BeanGetter.lookupLoginDataFacade().find(this.login);
        this.favouriteLoc = this.user.getLocale();
        SessionLocaleResolver slr = BeanGetter.getLocaleResolver(request);
        slr.setLocale(request, response, this.favouriteLoc);
    }
    
    public void refreshUserFromDB()
    {
        this.user = BeanGetter.lookupUserFacade().find(this.login);
        this.loginData = BeanGetter.lookupLoginDataFacade().find(this.login);
        this.favouriteLoc = this.user.getLocale();
    }
    public void setLanguageInDB(HttpServletRequest request, Locale locale) throws MPException
    {
        try {
            this.user.setLocale(locale);
            BeanGetter.lookupUserBean().editUser(this.user, this.loginData);
        } catch (MPException ex) {
            MPLogger.severe("Error while persisting language at UserSession");
            LocaleProvider loc = BeanGetter.getLocaleProvider(request);
            throw new MPException(loc.getMessage("session.errorWhilePersist", null, request.getLocale()));
        } catch (NullPointerException ex)
        {
            MPLogger.severe("Error while persisting language at UserSession because user is not in the session");
        }
    }

    public String getLogin() {
        return login;
    }
    
    public String getName()
    {
        return this.user.getName();
    }
    
    public String getSurname()
    {
        return this.user.getSurname();
    }

    public Locale getFavouriteLoc() {
        return favouriteLoc;
    }
}
