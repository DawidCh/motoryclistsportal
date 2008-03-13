/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import utils.MPException;

/**
 *
 * @author Dawid
 */
@Local
public interface UserLocal {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void createUser(entities.User user, entities.LoginData loginData) throws MPException;

    @RolesAllowed({"admins", "moders", "users"})
    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void editUser(entities.User user, entities.LoginData loginData) throws utils.MPException;
    
}
