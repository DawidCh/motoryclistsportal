/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import org.apache.log4j.Logger;

/**
 * Custom exception class with logging enabled.
 * @author kalosh
 */
public class MPException extends Exception {

    /**
     * Values for identyfing exception source. Login exists here.
     */
    public static final String LOGIN_EXISTS = "Login exists";

    /**
     * Values for identyfing exception source. Other reason here.
     */
    public static final String OTHER_ISSUE = "Other reason";

    /**
     * Default constructor whithout message and without logging.
     */
    public MPException() {
        super();
    }

    /**
     * Constructor whith message and logging.
     * @param message message to log.
     */
    public MPException(String message) {
        super(message);
        Logger.getLogger("E").error("From exception: "+message);
    }
}
