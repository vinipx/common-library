package com.ecosystem.common.exception;

/**
 * Exception thrown when input fails validation (HTTP 422).
 */
public class ValidationException extends BaseException {

    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private static final int HTTP_STATUS = 422;

    /**
     * Constructs a new {@code ValidationException} with the given message.
     *
     * @param message a human-readable description of the validation error
     */
    public ValidationException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    /**
     * Constructs a new {@code ValidationException} with a message and cause.
     *
     * @param message a human-readable description of the validation error
     * @param cause   the underlying cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }
}
