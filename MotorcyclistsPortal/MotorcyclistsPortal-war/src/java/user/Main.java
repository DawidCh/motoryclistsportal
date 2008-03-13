/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import utils.BeanGetter;
import utils.LocaleProvider;

/**
 *
 * @author kalosh
 */
public class Main extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.setRequireSession(true);
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        UserSession userSession = BeanGetter.getUserSession(request);
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        userSession.setValues(request, response);
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("pageTitle", localeProvider.getMessage("main.pageTitle", null, request.getLocale()));
        // </editor-fold>
        return new ModelAndView("secured/main", info);
    }
}
