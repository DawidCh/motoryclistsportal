/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author kalosh
 */
public class Credentials implements Controller{

    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        String reqURI = arg0.getRequestURI();
        if(reqURI.split("/")[2].equals("logout.html"))
        {
            HttpSession session = arg0.getSession(false);
            if(null != session)
                session.invalidate();
            return new ModelAndView("index");
        }
        else if(reqURI.split("/")[2].equals("login.html"))
        {
            return new ModelAndView("login");
        }
        return new ModelAndView("index");
    }

}
