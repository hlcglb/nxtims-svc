package com.hyundaiuni.nxtims.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 7875484667863167912L;

    private final String code;

    public ServiceException(String code, String message) {
        this(code, message, null);
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
