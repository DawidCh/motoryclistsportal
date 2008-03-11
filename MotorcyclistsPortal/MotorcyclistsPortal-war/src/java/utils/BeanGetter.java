/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;

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
}
