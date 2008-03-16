/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import javax.ejb.Local;
import utils.MPException;

/**
 *
 * @author Dawid
 */
@Local
public interface UserLocal {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void createUser(entities.User user) throws MPException;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void editUser(entities.User user) throws utils.MPException;
    
}
