/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import org.apache.log4j.Logger;

/**
 *
 * @author kalosh
 */
public class MPException extends Exception {
    
    public static String LOGIN_EXISTS = "Login exists";
    public static String OTHER_ISSUE = "Other reason";

    public MPException(String message) {
        super(message);
        //todo:przerobić logowanie: logwanie przebiegu kodu, metod,
        //logowanie rozmytości
        Logger.getLogger("E").error(message);
    }
}
