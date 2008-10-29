/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;
import java.util.Locale;

/**
 * Class for providing application settings.
 * @author Dawid Chojnacki
 */
public class ApplicationSettings {

    /**
     * Filed which is holding available locales in the application.
     */
    private List<Locale> availableLanguages;
    /**
     * Default language.
     */
    private static Locale locale = new Locale("pl_PL");
    /**
     * Minimum password length.
     */
    private static final int MIN_PASS_LENGTH = 5;
    /**
     * Maximum password length.
     */
    private static final int MAX_PASS_LENGTH = 5;
    /**
     * Password length (min, max).
     */
    private static int[] passLength = {MIN_PASS_LENGTH, MAX_PASS_LENGTH};
    /**
     * Colour of successed message.
     */
    private static String succColour = "green";
    /**
     * Colour of failure message.
     */
    private static String failColour = "red";

    /**
     * Constructor.
     */
    public ApplicationSettings() {
        this.availableLanguages =
                BeanGetter.lookupAvailableLangsFacade().findAll();
    }

    /**
     * Getter for default locale.
     * @return default localization object
     */
    public static Locale getLocale() {
        return ApplicationSettings.locale;
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

    /**
     * Method provides available languages.
     * @return List of Locale objects
     */
    public List<Locale> getAvailableLanguages() {
        return availableLanguages;
    }
}
