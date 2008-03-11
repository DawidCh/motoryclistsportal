/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Locale;

/**
 *
 * @author kalosh
 */
public class DefaultValues {
    private Locale locale;

    public DefaultValues() {
        this.locale = new Locale("pl_PL");
    }

    public Locale getLocale() {
        return this.locale;
    }
}
