/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.logging.Logger;

/**
 *
 * @author kalosh
 */
public class MPLogger {

    public static void severe(String message) {
        Logger.getLogger("global").severe(message);
    }
}
