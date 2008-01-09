/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author kalosh
 */
public class Test implements Controller{

    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        String name = arg0.getParameter("name");
        return new ModelAndView("testView", "name", name);
    }

}
