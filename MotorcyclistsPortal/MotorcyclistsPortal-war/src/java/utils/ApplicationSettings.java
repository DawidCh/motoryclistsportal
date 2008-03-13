/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import entities.AvailableLangs;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kalosh
 */
public class ApplicationSettings {
    private List<AvailableLangs> availableLanguages;

    public ApplicationSettings() {
        this.availableLanguages = BeanGetter.lookupAvailableLangsFacade().findAll();
        
    }

    public List<Locale> getAvailableLanguages(HttpServletRequest request) {
        List<Locale> result = new ArrayList<Locale>();
        for (int i = 0; i < availableLanguages.size(); i++) {
            AvailableLangs availableLangs = availableLanguages.get(i);
            result.add(new Locale(availableLangs.getLocale()));
        }
        return result;
    }

    
}
