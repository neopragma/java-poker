package com.neopragma.helpers;

import com.neopragma.preconditions.Precondition;

public class MessageId {

    private String value;

    /**
     * Wraps a String identifier for messages in the 'messages' ResourceBundle.
     * @param value the String value of the identifier, must be M followed by 3 digits.
     *             <br/>
     * @author neopragma
     * @since 1.8
     */
    public MessageId(String value) {
        Precondition.assertThat(value.matches("M\\d{3}"),
                "MessageId format is M000 (letter M followed by 3 digits)");
        this.value = value;
    }

    public String value() {
        return value;
    }
}
