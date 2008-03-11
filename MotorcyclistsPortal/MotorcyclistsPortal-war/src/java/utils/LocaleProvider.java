/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Locale;
import org.springframework.context.MessageSource;

/**
 *
 * @author kalosh
 */
public class LocaleProvider {
    MessageSource locales;
    
    public LocaleProvider()
    {
        
    }
    
    public void setLocales(MessageSource locales)
    {
        this.locales = locales;
    }
    
    public String getMessage(String key, Object[] objects, Locale locale)
    {
        return this.locales.getMessage(key, objects, locale);
    }
}
