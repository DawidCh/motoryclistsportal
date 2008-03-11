/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import utils.BeanGetter;

/**
 *
 * @author kalosh
 */
public class Main implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
        UserSession userSession = (UserSession) BeanGetter.getScopedBean("userSession", request);
        userSession.setValues(request);
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("login", userSession.getLogin());
        return new ModelAndView("secured/main", info);
    }
}
