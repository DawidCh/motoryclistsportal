/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author kalosh
 */
public abstract class MPLogger {

    private static Logger console = Logger.getLogger("stdout");
    
    private static Logger debug = Logger.getLogger("DEBUG");
    private static Logger info = Logger.getLogger("INFO");
    private static Logger warning = Logger.getLogger("WARN");
    private static Logger fatal = Logger.getLogger("FATAL");
    private static Logger error = Logger.getLogger("ERROR");
    

    static {
        console.setLevel(Level.ALL);
        
        debug.setLevel(Level.DEBUG);
        info.setLevel(Level.INFO);
        warning.setLevel(Level.WARN);
        fatal.setLevel(Level.FATAL);
        error.setLevel(Level.ERROR);
    }

    public static void debug(String message) {
        debug.debug(message);
    }

    public static void info(String message) {
        info.info(message);
    }

    public static void warning(String message) {
        warning.warn(message);
    }

    public static void fatal(String message) {
        fatal.fatal(message);
    }

    public static void error(String message) {
        error.error(message);
    }
}
