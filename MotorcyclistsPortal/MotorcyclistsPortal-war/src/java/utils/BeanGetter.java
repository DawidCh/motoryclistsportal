/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import facades.AvailableLangsFacadeLocal;
import facades.LoginDataFacadeLocal;
import facades.MotorcycleFacadeLocal;
import facades.PrivilegesFacadeLocal;
import facades.UserFacadeLocal;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import services.UserLocal;
import user.UserSession;

/**
 *
 * @author kalosh
 */
public class BeanGetter {

    public static UserSession getUserSession(HttpServletRequest request){
        return (UserSession) BeanGetter.getScopedBean("userSession", request);
    }
    
    public static LocaleProvider getLocaleProvider(HttpServletRequest request){
        return (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
    }
    
    public static SessionLocaleResolver getLocaleResolver(HttpServletRequest request){
        return (SessionLocaleResolver) BeanGetter.getScopedBean("localeResolver", request);
    }
    
    public static ApplicationSettings getApplicationSettings(HttpServletRequest request){
        return (ApplicationSettings) BeanGetter.getScopedBean("settings", request);
    }
    
    private static Object getScopedBean(String beanId, HttpServletRequest request)
    {
         ApplicationContext ac = org.springframework.web.context.support.
           WebApplicationContextUtils.getRequiredWebApplicationContext(
                               request.getSession().getServletContext());
         return ac.getBean(beanId);
    }

    public static UserLocal lookupUserBean() {
        try {
            Context c = new InitialContext();
            return (UserLocal) c.lookup("java:comp/env/UserBean");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up UserBean in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static PrivilegesFacadeLocal lookupPrivilegesFacade() {
        try {
            Context c = new InitialContext();
            return (PrivilegesFacadeLocal) c.lookup("java:comp/env/PrivilegesFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up PrivilegesFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static UserFacadeLocal lookupUserFacade() {
        try {
            Context c = new InitialContext();
            return (UserFacadeLocal) c.lookup("java:comp/env/UserFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up UserFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static LoginDataFacadeLocal lookupLoginDataFacade() {
        try {
            Context c = new InitialContext();
            return (LoginDataFacadeLocal) c.lookup("java:comp/env/LoginDataFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up LoginFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static AvailableLangsFacadeLocal lookupAvailableLangsFacade() {
        try {
            Context c = new InitialContext();
            return (AvailableLangsFacadeLocal) c.lookup("java:comp/env/AvailableLangsFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up AvailableLangs in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static MotorcycleFacadeLocal lookupMotorcycleFacade() {
        try {
            Context c = new InitialContext();
            return (MotorcycleFacadeLocal) c.lookup("java:comp/env/MotorcycleFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up MotorcycleFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }
}
