package com.ecosystem.common.exception;

/**
 * Exception thrown when a requested resource is not found (HTTP 404).
 */
public class NotFoundException extends BaseException {

    private static final String ERROR_CODE = "NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    /**
     * Constructs a new {@code NotFoundException} with the given message.
     *
     * @param message a human-readable description of the missing resource
     */
    public NotFoundException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    /**
     * Constructs a new {@code NotFoundException} with a message and cause.
     *
     * @param message a human-readable description of the missing resource
     * @param cause   the underlying cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }
}
