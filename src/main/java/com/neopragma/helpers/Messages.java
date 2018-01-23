package com.neopragma.helpers;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Looks up a message from a ResourceBundle and inserts substitution values into the text using {MessageFormat#format}.
 */
public class Messages {

    private static final String NOT_FOUND = "Message id {0} not found";
    private static ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.US);

    /**
     * Looks up a message string from a ResourceBundle.
     * @param id for looking up the message in a ResourceBundle named "messages".
     * @return String message text from the ResourceBundle.
     */
    public static String message(String id) {
        return message(id, null);
    }

    /**
     * Looks up a message string from a ResourceBundle and applies any substitution values that were passed.
     * @param id for looking up the message in a ResourceBundle named "messages".
     * @param substitutionValues to insert into the message text, if there are any placeholders.
     * @return String message text will substitution values applied.
     */
    public static String message(String id, String...substitutionValues) {
        String message = null;
        try {
            message = messages.getString(id);
        } catch (MissingResourceException mre) {
            message = NOT_FOUND;
        } catch (Exception unexpectedException) {
            throw new RuntimeException("Unexpected exception accessing resource bundle 'messages'",
                    unexpectedException);
        }
        return MessageFormat.format(message, substitutionValues);
    }
}
