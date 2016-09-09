package com.hyundaiuni.nxtims.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 7875484667863167912L;

    private final String code;
    private final String[] args;

    public ServiceException(String code, String message, String[] args) {
        this(code, message, args, null);
    }

    public ServiceException(String code, String message, String[] args, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public String[] getArgs() {
        return args;
    }
}
