/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entities.User;
import facades.LoginDataFacadeLocal;
import facades.UserFacadeLocal;
import javax.annotation.security.PermitAll;
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
    public void createUser(User user) throws MPException
    {
        this.userFacade.create(user);
    }
 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editUser(User user) throws MPException
    {
        this.userFacade.edit(user);
    }
}
