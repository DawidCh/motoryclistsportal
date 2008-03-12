/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class UserSession {
    
    Locale currentLocale;
    String login;

    private UserSession() {
    }
    
    public void setValues(HttpServletRequest request)
    {
        
        this.login = request.getUserPrincipal().getName();
        this.currentLocale = request.getLocale();
    }
    
    public Locale getLanguage()
    {
        return this.currentLocale;
    }
    
    public void setLanguage(String language)
    {
        this.currentLocale = new Locale(language);
    }

    public String getLogin() {
        return login;
    }

}
