/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface UserLocal {

    public void createUser(entities.User user, entities.LoginData loginData);
    
}
