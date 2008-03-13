/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import entities.LoginData;
import entities.User;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import utils.BeanGetter;
import utils.DefaultValues;
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
    
    public void setValues(HttpServletRequest request)
    {
        this.login = request.getUserPrincipal().getName();
        this.user = BeanGetter.lookupUserFacade().find(this.login);
        this.loginData = BeanGetter.lookupLoginDataFacade().find(this.login);
        this.favouriteLoc = this.user.getLocale();
    }
    public void setLanguageInDB(HttpServletRequest request) throws MPException
    {
        try {
            this.user.setLocale(request.getLocale());
            BeanGetter.lookupUserBean().editUser(this.user, this.loginData);
        } catch (MPException ex) {
            MPLogger.severe("Error while persisting language at UserSession");
            LocaleProvider loc = (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
            throw new MPException(loc.getMessage("session.errorWhilePersist", null, request.getLocale()));
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
}
