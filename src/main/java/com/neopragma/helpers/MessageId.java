package com.neopragma.helpers;

import com.neopragma.preconditions.Precondition;

public class MessageId {

    private String messageId;

    public MessageId(String messageId) {
        Precondition.assertThat(messageId.matches("M\\d{3}"),
                "MessageId format is M000 (letter M followed by 3 digits)");
        this.messageId = messageId;
    }

    public String value() {
        return messageId;
    }
}
