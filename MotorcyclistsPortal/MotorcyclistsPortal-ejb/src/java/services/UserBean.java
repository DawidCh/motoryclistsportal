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
import utils.MPLogger;

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
    public void createUser(User user) throws MPException
    {
        //this.loginDataFacade.create(user.getLoginData());
        this.userFacade.create(user);
    }
 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editUser(User user) throws MPException
    {
        //this.loginDataFacade.edit(user.getLoginData());
        this.userFacade.edit(user);
        MPLogger.severe(user.getLoginData().getPassword());
    }
}
