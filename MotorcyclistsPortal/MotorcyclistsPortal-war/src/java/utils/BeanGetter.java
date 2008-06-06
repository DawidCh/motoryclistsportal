/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import facades.ActionFacadeLocal;
import facades.ActivityPeriodFacadeLocal;
import facades.AvailableLangsFacadeLocal;
import facades.FishierElementBridgeFacadeLocal;
import facades.FishierFacadeLocal;
import facades.FishiersElementFacadeLocal;
import facades.LoginDataFacadeLocal;
import facades.MotorcycleFacadeLocal;
import facades.PrivilegesFacadeLocal;
import facades.TripFacadeLocal;
import facades.TripTypeFacadeLocal;
import facades.UserFacadeLocal;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import security.DetailedUserInformation;

/**
 *
 * @author kalosh
 */
public class BeanGetter {

    /*public static UserSession getUserSession(HttpServletRequest request){
        return (UserSession) BeanGetter.getScopedBean("userSession", request);
    }*/
    //todo: make to throw new exception when user is not logged in
    public static DetailedUserInformation getUserInfo()
    {
        return (DetailedUserInformation)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public static LocaleProvider getLocaleProvider(HttpServletRequest request){
        return (LocaleProvider) BeanGetter.getScopedBean("localeProvider", request);
    }
    
    public static SessionLocaleResolver getSessionLocaleResolver(HttpServletRequest request){
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

    public static FishierFacadeLocal lookupFishierFacade() {
        try {
            Context c = new InitialContext();
            return (FishierFacadeLocal) c.lookup("java:comp/env/FishierFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up FishierFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static ActivityPeriodFacadeLocal lookupActivityPeriodFacade() {
        try {
            Context c = new InitialContext();
            return (ActivityPeriodFacadeLocal) c.lookup("java:comp/env/ActivityPeriodFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up ActivityPeriodFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static FishiersElementFacadeLocal lookupFishiersElementFacade() {
        try {
            Context c = new InitialContext();
            return (FishiersElementFacadeLocal) c.lookup("java:comp/env/FishiersElementFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up FishiersElementFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static FishierElementBridgeFacadeLocal lookupFishierElementBridgeFacade() {
        try {
            Context c = new InitialContext();
            return (FishierElementBridgeFacadeLocal) c.lookup("java:comp/env/FishierElementBridgeFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up FishierElementBridgeFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static ActionFacadeLocal lookupActionFacade() {
        try {
            Context c = new InitialContext();
            return (ActionFacadeLocal) c.lookup("java:comp/env/ActionFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up ActionFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }
    
    public static TripFacadeLocal lookupTripFacade() {
        try {
            Context c = new InitialContext();
            return (TripFacadeLocal) c.lookup("java:comp/env/TripFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up TripFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    public static TripTypeFacadeLocal lookupTripTypeFacade() {
        try {
            Context c = new InitialContext();
            return (TripTypeFacadeLocal) c.lookup("java:comp/env/TripTypeFacade");
        } catch (NamingException ne) {
            MPLogger.severe("Exception while looking up TripTypeFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }
}
