package com.ecosystem.common.exception;

/**
 * Exception thrown when a resource conflict is detected (HTTP 409).
 */
public class ConflictException extends BaseException {

    private static final String ERROR_CODE = "CONFLICT";
    private static final int HTTP_STATUS = 409;

    /**
     * Constructs a new {@code ConflictException} with the given message.
     *
     * @param message a human-readable description of the conflict
     */
    public ConflictException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    /**
     * Constructs a new {@code ConflictException} with a message and cause.
     *
     * @param message a human-readable description of the conflict
     * @param cause   the underlying cause
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }
}
