package utils;

import facades.ActionFacadeLocal;
import facades.ActivityPeriodFacadeLocal;
import facades.AvailableLangsFacadeLocal;
import facades.DistanceFacadeLocal;
import facades.FishierElementBridgeFacadeLocal;
import facades.FishierFacadeLocal;
import facades.FishiersElementFacadeLocal;
import facades.FuzzyAdviseFacadeLocal;
import facades.FuzzyDecisionFacadeLocal;
import facades.LoginDataFacadeLocal;
import facades.MotorcycleFacadeLocal;
import facades.PrivilegesFacadeLocal;
import facades.TripFacadeLocal;
import facades.TripTypeFacadeLocal;
import facades.UsageFacadeLocal;
import facades.UserFacadeLocal;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import security.DetailedUserInformation;

/**
 * Utility class, used for providing varius beans from varius scopes.
 * @author Dawid Chojnacki
 */
public class BeanGetter {

    /**
     * Private constructor.
     */
    private BeanGetter() {
    }

    /**
     * Method used for getting user information from security context.
     * @return DetailedUserInformation
     * @throws utils.MPException when user is not logged in
     */
    public static DetailedUserInformation getUserInfo() throws MPException {
        if(!SecurityContextHolder.getContext().
                getAuthentication().isAuthenticated()){
            throw new MPException("User not logged in");
        }
        return (DetailedUserInformation)
                SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
    }

    /**
     * Method used for getting LocaleProvider.
     * @param request HTTP request
     * @return LocaleProvider object
     */
    public static LocaleProvider getLocaleProvider(HttpServletRequest request) {
        return (LocaleProvider)
                BeanGetter.getObjectFromSessionScope("localeProvider", request);
    }

    /**
     * Method used for getting SessionLocaleResolver.
     * @param request HTTP request
     * @return SessionLocaleResolver object
     */
    public static SessionLocaleResolver
            getSessionLocaleResolver(HttpServletRequest request){
        return (SessionLocaleResolver)
                BeanGetter.getObjectFromSessionScope("localeResolver", request);
    }

    /**
     * Method used for getting ApplicationSettings.
     * @param request HTTP request
     * @return ApplicationSettings object
     */
    public static ApplicationSettings getApplicationSettings(
            HttpServletRequest request){
        return (ApplicationSettings)
                BeanGetter.getObjectFromSessionScope("settings", request);
    }

    /**
     * Method used for getting bean object from session scope.
     * @param request HTTP request
     * @return ApplicationSettings object
     */
    private static Object getObjectFromSessionScope(String beanId,
            HttpServletRequest request)
    {
         ApplicationContext ac = org.springframework.web.context.support.
           WebApplicationContextUtils.getRequiredWebApplicationContext(
                               request.getSession().getServletContext());
         return ac.getBean(beanId);
    }

    /**
     * Method used for getting PrivilegesFacadeLocal.
     * @return PrivilegesFacadeLocal object
     */
    public static PrivilegesFacadeLocal lookupPrivilegesFacade() {
        try {
            Context c = new InitialContext();
            return (PrivilegesFacadeLocal)
                    c.lookup("java:comp/env/PrivilegesFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking up"
                    + "PrivilegesFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting UserFacadeLocal.
     * @return UserFacadeLocal object
     */
    public static UserFacadeLocal lookupUserFacade() {
        try {
            Context c = new InitialContext();
            return (UserFacadeLocal) c.lookup("java:comp/env/UserFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up UserFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting LoginDataFacadeLocal.
     * @return LoginDataFacadeLocal object
     */
    public static LoginDataFacadeLocal lookupLoginDataFacade() {
        try {
            Context c = new InitialContext();
            return (LoginDataFacadeLocal)
                    c.lookup("java:comp/env/LoginDataFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up LoginFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting AvailableLangsFacadeLocal.
     * @return AvailableLangsFacadeLocal object
     */
    public static AvailableLangsFacadeLocal lookupAvailableLangsFacade() {
        try {
            Context c = new InitialContext();
            return (AvailableLangsFacadeLocal)
                    c.lookup("java:comp/env/AvailableLangsFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up AvailableLangs in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting MotorcycleFacadeLocal.
     * @return MotorcycleFacadeLocal object
     */
    public static MotorcycleFacadeLocal lookupMotorcycleFacade() {
        try {
            Context c = new InitialContext();
            return (MotorcycleFacadeLocal)
                    c.lookup("java:comp/env/MotorcycleFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up MotorcycleFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting FishierFacadeLocal.
     * @return FishierFacadeLocal object
     */
    public static FishierFacadeLocal lookupFishierFacade() {
        try {
            Context c = new InitialContext();
            return (FishierFacadeLocal) c.lookup("java:comp/env/FishierFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking "
                    + "up FishierFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting ActivityPeriodFacadeLocal.
     * @return ActivityPeriodFacadeLocal object
     */
    public static ActivityPeriodFacadeLocal lookupActivityPeriodFacade() {
        try {
            Context c = new InitialContext();
            return (ActivityPeriodFacadeLocal)
                    c.lookup("java:comp/env/ActivityPeriodFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up ActivityPeriodFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting FishiersElementFacadeLocal.
     * @return FishiersElementFacadeLocal object
     */
    public static FishiersElementFacadeLocal lookupFishiersElementFacade() {
        try {
            Context c = new InitialContext();
            return (FishiersElementFacadeLocal)
                    c.lookup("java:comp/env/FishiersElementFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up FishiersElementFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting FishierElementBridgeFacadeLocal.
     * @return FishierElementBridgeFacadeLocal object
     */
    public static FishierElementBridgeFacadeLocal
            lookupFishierElementBridgeFacade() {
        try {
            Context c = new InitialContext();
            return (FishierElementBridgeFacadeLocal)
                    c.lookup("java:comp/env/FishierElementBridgeFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking "
                    + "up FishierElementBridgeFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting ActionFacadeLocal.
     * @return ActionFacadeLocal object
     */
    public static ActionFacadeLocal lookupActionFacade() {
        try {
            Context c = new InitialContext();
            return (ActionFacadeLocal) c.lookup("java:comp/env/ActionFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking"
                    + "up ActionFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting TripFacadeLocal.
     * @return TripFacadeLocal object
     */
    public static TripFacadeLocal lookupTripFacade() {
        try {
            Context c = new InitialContext();
            return (TripFacadeLocal)
                    c.lookup("java:comp/env/TripFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while"
                    + "looking up TripFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting TripTypeFacadeLocal.
     * @return TripTypeFacadeLocal object
     */
    public static TripTypeFacadeLocal lookupTripTypeFacade() {
        try {
            Context c = new InitialContext();
            return (TripTypeFacadeLocal)
                    c.lookup("java:comp/env/TripTypeFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while"
                    + "looking up TripTypeFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting UsageFacadeLocal.
     * @return UsageFacadeLocal object
     */
    public static UsageFacadeLocal lookupUsageFacade() {
        try {
            Context c = new InitialContext();
            return (UsageFacadeLocal) c.lookup("java:comp/env/UsageFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking up"
                    + "TripTypeFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting DistanceFacadeLocal.
     * @return DistanceFacadeLocal object
     */
    public static DistanceFacadeLocal lookupDistanceFacade() {
        try {
            Context c = new InitialContext();
            return (DistanceFacadeLocal)
                    c.lookup("java:comp/env/DistanceFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking up"
                    + "DistanceFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting FuzzyAdviseFacadeLocal.
     * @return FuzzyAdviseFacadeLocal object
     */
    public static FuzzyAdviseFacadeLocal lookupFuzzyAdviseFacade() {
        try {
            Context c = new InitialContext();
            return (FuzzyAdviseFacadeLocal)
                    c.lookup("java:comp/env/FuzzyAdviseFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking up"
                    + "FuzzyAdviseFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }

    /**
     * Method used for getting FuzzyDecisionFacadeLocal.
     * @return FuzzyDecisionFacadeLocal object
     */
    public static FuzzyDecisionFacadeLocal lookupFuzzyDecisionFacade() {
        try {
            Context c = new InitialContext();
            return (FuzzyDecisionFacadeLocal)
                    c.lookup("java:comp/env/FuzzyDecisionFacade");
        } catch (NamingException ne) {
            Logger.getLogger("E").error("Exception while looking up"
                    + "FuzzyDecisionFacade in BeanGetter");
            throw new RuntimeException(ne);
        }
    }
}
