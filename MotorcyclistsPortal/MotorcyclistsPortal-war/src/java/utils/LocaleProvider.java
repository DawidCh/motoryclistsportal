/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Locale;
import org.springframework.context.MessageSource;

/**
 * Class which holds current locales for user.
 * @author kalosh
 */
public class LocaleProvider {

    /**
     * Resource messages from file.
     */
    private MessageSource messages;

    /**
     * Default constructor.
     */
    public LocaleProvider() {

    }

    /**
     * Set current locales.
     * @param locales current locales for user
     */
    public void setLocales(MessageSource messages) {
        this.messages = messages;
    }

    /**
     * Method which provides message selected by given value.
     * @param key message key
     * @param objects argument of the message
     * @param locale source locale for message
     * @return
     */
    public String getMessage(String key, Object[] objects, Locale locale) {
        return this.messages.getMessage(key, objects, locale);
    }
}
