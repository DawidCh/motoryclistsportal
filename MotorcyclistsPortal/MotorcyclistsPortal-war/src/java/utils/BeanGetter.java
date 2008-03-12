/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import entities.Privileges;
import facades.PrivilegesFacadeLocal;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import utils.MPLogger;
import org.springframework.context.ApplicationContext;
import services.UserLocal;

/**
 *
 * @author kalosh
 */
public class BeanGetter {

    public static Object getScopedBean(String beanId, HttpServletRequest request)
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
}
