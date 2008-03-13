/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author kalosh
 */
public class ApplicationSettings {
    private List<Locale> availableLanguages;

    public ApplicationSettings() {
        this.availableLanguages = BeanGetter.lookupAvailableLangsFacade().findAll();
        
    }

    public List<Locale> getAvailableLanguages() {
        return availableLanguages;
    }
}
