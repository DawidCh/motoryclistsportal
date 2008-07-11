/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation.controllers.secured;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.RequestContextUtils;
import utils.BeanGetter;
import utils.LocaleProvider;

/**
 * Controller for main page
 * @author Dawid Chojnacki
 */
public class Main extends AbstractController {

    /**
     * Method used for showing main page.
     * @param request HTTP request object
     * @param response HTTP response object
     * @return ModelAndView object which contains map of object passed to page
     * and chosen page for display
     * @throws java.lang.Exception
     */
    @Override
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Logger.getLogger("E").trace("Entering to: handleRequestInternal");
        // <editor-fold defaultstate="collapsed" desc="Generated vars: localeProvider, defaultLocale,formInfo and put vars into">
        LocaleProvider localeProvider = BeanGetter.getLocaleProvider(request);
        HashMap < String, Object > info = new HashMap < String, Object >();
        info.put("pageTitle", localeProvider.getMessage("main.pageTitle", null,
                RequestContextUtils.getLocale(request)));
        // </editor-fold>
        Logger.getLogger("E").trace("Exiting from: handleRequestInternal");
        return new ModelAndView("secured/main", info);
    }
}
