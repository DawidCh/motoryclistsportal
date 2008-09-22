/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.Authentication;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import utils.BeanGetter;

/**
 * Custom AuthenticationProcessingFilter class. It sets user's default
 * locale after log in.
 * @author Dawid Chojnacki
 */
public class MPAuthenticationProcessingFilter
        extends AuthenticationProcessingFilter {

    /**
     * Method for setting user's default locale after log in.
     * @param request HTTP request
     * @param response HTTP response
     * @param authentication Authentication object
     * @throws java.io.IOException
     */
    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException {
        super.onSuccessfulAuthentication(request, response, authentication);
        SessionLocaleResolver slr =
                BeanGetter.getSessionLocaleResolver(request);
        slr.setLocale(request, response,
                ((DetailedUserInformation) authentication.
                getPrincipal()).getLocale());
    }
}
