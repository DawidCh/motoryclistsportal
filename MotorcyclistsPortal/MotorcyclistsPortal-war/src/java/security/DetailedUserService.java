/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package security;

import entities.User;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import utils.BeanGetter;

/**
 *
 * @author kalosh
 */
public class DetailedUserService implements UserDetailsService {

    public DetailedUserInformation loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        DetailedUserInformation userInfo;
        User user = BeanGetter.lookupUserFacade().find(login);
        userInfo = new DetailedUserInformation(user);
        return userInfo;
    }

}
