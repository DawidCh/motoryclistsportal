/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author kalosh
 */
public class IndexController implements Controller{

    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        String message="pupka";
        return new ModelAndView("index");
    }

}
