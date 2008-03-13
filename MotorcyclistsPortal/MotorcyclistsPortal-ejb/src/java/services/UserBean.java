/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entities.LoginData;
import entities.User;
import facades.LoginDataFacadeLocal;
import facades.UserFacadeLocal;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import utils.MPException;

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

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createUser(User user, LoginData loginData) throws MPException
    {
        this.loginDataFacade.create(loginData);
        this.userFacade.create(user);
    }
 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @RolesAllowed({"admins", "moders", "users"})
    public void editUser(User user, LoginData loginData) throws MPException
    {
        this.loginDataFacade.edit(loginData);
        this.userFacade.edit(user);
    }
}
