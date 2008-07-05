/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Locale;

/**
 * Class wich contains default values of many variables for all application.
 * @author kalosh
 */
public class DefaultValues {

    /**
     * Default language.
     */
    private static Locale locale = new Locale("pl_PL");

    /**
     * Password length (min, max).
     */
    private static int[] passLength = {5,10};

    /**
     * Colour of successed message.
     */
    private static String succColour = "green";

    /**
     * Colour of failure message.
     */
    private static String failColour = "red";

    /**
     * Private constructor.
     */
    private DefaultValues() {
    }

    /**
     * Getter for default locale.
     * @return default localization object
     */
    public static Locale getLocale() {
        return DefaultValues.locale;
    }

    /**
     * Getter for password length.
     * @return array of int where first is minimum and second maximum
     * password length
     */
    public static int[] getPassLength() {
        return passLength;
    }

    /**
     * Getter for fail colour.
     * @return fail colour as String
     */
    public static String getFailColour() {
        return failColour;
    }

    /**
     * Getter for success colour.
     * @return success colour as String
     */
    public static String getSuccColour() {
        return succColour;
    }
}
