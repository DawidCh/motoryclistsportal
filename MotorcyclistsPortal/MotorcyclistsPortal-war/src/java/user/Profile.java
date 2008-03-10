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
public class Profile implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String, Object> profile = new HashMap<String, Object>();
        UserSession us = (UserSession) BeanGetter.getBean("userSession", request);
        us.setValues(request.getUserPrincipal().getName());
        profile.put("login", request.getUserPrincipal().getName());
        return new ModelAndView("user/profile",profile);
    }

}
