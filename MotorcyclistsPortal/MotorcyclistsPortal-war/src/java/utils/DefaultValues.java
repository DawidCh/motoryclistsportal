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
    private static Locale locale = new Locale("pl_PL");
    private static int[] passLength = {5,10};

    public static Locale getLocale() {
        return DefaultValues.locale;
    }

    public static int[] getPassLength() {
        return passLength;
    }
}
