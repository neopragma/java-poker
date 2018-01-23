package com.neopragma.preconditions;

public class PreconditionNotMetException extends RuntimeException {

    private String message;

    public PreconditionNotMetException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
