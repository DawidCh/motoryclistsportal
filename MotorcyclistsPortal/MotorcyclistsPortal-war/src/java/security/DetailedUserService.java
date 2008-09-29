/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package security;

import entities.User;
import org.apache.log4j.Logger;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import utils.BeanGetter;

/**
 *
 * @author Dawid Chojnacki
 */
public class DetailedUserService implements UserDetailsService {

    /**
     * Method used for providing details about logged in user.
     * @param login user's login
     * @return DetailedUserInformation object.
     * @throws org.springframework.security.userdetails.
     * UsernameNotFoundException
     * @throws org.springframework.dao.DataAccessException
     */
    public DetailedUserInformation loadUserByUsername(String login)
            throws UsernameNotFoundException, DataAccessException {
        DetailedUserInformation userInfo;
        User user = BeanGetter.lookupUserFacade().find(login);
        if(user == null){
            Logger.getLogger("errorLogger").error("login: "+login);
            throw new UsernameNotFoundException(login);
        }
        userInfo = new DetailedUserInformation(user);
        return userInfo;
    }

}
