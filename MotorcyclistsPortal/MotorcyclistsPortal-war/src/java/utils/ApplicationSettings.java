/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.List;
import java.util.Locale;

/**
 * Class for providing application settings.
 * @author kalosh
 */
public class ApplicationSettings {
    /**
     * Filed which is holding available locales in the application.
     */
    private List < Locale > availableLanguages;

    /**
     * Constructor.
     */
    public ApplicationSettings() {
        this.availableLanguages =
                BeanGetter.lookupAvailableLangsFacade().findAll();
    }

    /**
     * Method provides available languages.
     * @return List of Locale objects
     */
    public List < Locale > getAvailableLanguages() {
        return availableLanguages;
    }
}
