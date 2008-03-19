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
public abstract class MPLogger {

    public static void severe(String message)
    {
        Logger.getLogger("global").severe(message);
    }
    
    public static void finer(String message)
    {
        Logger.getLogger("global").finer(message);
    }
    
    public static void finest(String message)
    {
        Logger.getLogger("global").finest(message);
    }
    
    public static void fine(String message)
    {
        Logger.getLogger("global").fine(message);
    }
    
    public static void warning(String message)
    {
        Logger.getLogger("global").warning(message);
    }
    
    public static void info(String message)
    {
        Logger.getLogger("global").info(message);
    }
}
