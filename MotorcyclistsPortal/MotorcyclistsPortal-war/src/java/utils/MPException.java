/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author kalosh
 */
public class MPException extends Exception {
    
    public static String LOGIN_EXISTS = "Login exists";
    public static String OTHER_ISSUE = "Other reason";

    public MPException(String message) {
        super(message);
    }
}
