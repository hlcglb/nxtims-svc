package com.hyundaiuni.nxtims.exception;

public class MessageDigestException extends Exception {
    private static final long serialVersionUID = 2447133971626256442L;

    public MessageDigestException() {

    }

    public MessageDigestException(String message) {
        super(message);
    }

    public MessageDigestException(Throwable cause) {
        super(cause);
    }

    public MessageDigestException(String message, Throwable cause) {
        super(message, cause);
    }
}
