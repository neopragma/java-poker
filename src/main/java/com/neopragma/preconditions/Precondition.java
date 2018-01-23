package com.neopragma.preconditions;

import com.neopragma.helpers.MessageId;
import com.neopragma.helpers.Messages;

import java.text.MessageFormat;

/**
 * <p>
 * Provide support for asserting preconditions.
 * </p>
 * <p>
 * call
 * <ul>
 * <li>Precondition.assertThat(boolean expression, String message) <em>or</em></li>
 * <li>Precondition.assertThat(boolean expression, String value, String[] substitutionValues)</li>
 * </ul>
 * If the boolean expression is not true, assertThat throws PreconditionNotMetException and calls<br/>
 * MessageFormat.format() to populate any substitutionValues in the message from the ResourceBundle.<br/>
 * When the plain String message version of assertThat is called, it assumes the caller has formatted<br/>
 * the message already and does not refer to a ResourceBundle.<br/>
 * In either case, assertThat includes the caller's class name and method name in the exception message.
 * </p>
 * <p>
 * @see MessageFormat#format
 * @see Messages#message
 * </p>
 * @author neopragma
 * @since 1.8
 */
public class Precondition {

    private static String thisClassName = "Precondition";

    /**
     * Look up the message in a ResourceBundle
     * @param expression boolean expression defining the precondition
     * @param messageId identifier of the message to look up
     */
    public static void assertThat(boolean expression, MessageId messageId) {
        assertThat(expression, Messages.message(messageId.value()));
    }

    /**
     * Look up the message in a ResourceBundle
     * @param expression boolean expression defining the precondition
     * @param messageId identifier of the message to look up
     * @param substitutionValues values to insert into the message text
     */
    public static void assertThat(boolean expression, MessageId messageId, String...substitutionValues) {
        assertThat(expression, Messages.message(messageId.value(), substitutionValues));
    }

    /**
     * Format exception message and throw exception if precondition is not met
     * @param expression boolean expression defining the precondition
     * @param message message text to be included in the exception message text
     */
    public static void assertThat(boolean expression, String message) {
        if (expression) return;
        int stackDepth = findFirstStackEntryThatIsNotUs();
        throw new PreconditionNotMetException(
                MessageFormat.format("In {0}.{1}: {2}",
                        getClassName(stackDepth),
                        getMethodName(stackDepth),
                        message));
    }

    private static String getClassName(int stackDepth) {
        return Thread.currentThread().getStackTrace()[stackDepth].getClassName();
    }

    private static String getMethodName(int stackDepth) {
        return Thread.currentThread().getStackTrace()[stackDepth].getMethodName();
    }

    private static int findFirstStackEntryThatIsNotUs() {
        for (int i = 2; i < 8; i++) {
            if (getClassName(i).endsWith(thisClassName) == false) {
                return i-1;
            }
        }
        return 2;
    }
}
