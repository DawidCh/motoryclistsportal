/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entities.LoginData;
import entities.User;
import facades.LoginDataFacadeLocal;
import facades.UserFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Dawid
 */
@Stateless
public class UserBean implements UserLocal {
    @EJB
    private LoginDataFacadeLocal loginDataFacade;
    @EJB
    private UserFacadeLocal userFacade;

    public void createUser(User user, LoginData loginData)
    {
        this.userFacade.create(user);
        this.loginDataFacade.create(loginData);
    }
 
}
