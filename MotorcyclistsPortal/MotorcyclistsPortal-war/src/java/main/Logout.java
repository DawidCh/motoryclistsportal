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
public class Logout implements Controller{

    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        HttpSession session = arg0.getSession(false);
        if(null != session)
            session.invalidate();
        return new ModelAndView("index", "message", "Wylogowano poprawnie");
    }

}
