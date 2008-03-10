/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import main.MPLogger;

/**
 *
 * @author kalosh
 */
public class UserSession {

    public UserSession() {
    }
    
    public void setValues(String login)
    {
        MPLogger.severe(login);
    }

}
