package com.ecosystem.common.exception;

/**
 * Abstract base exception for all custom exceptions in the ecosystem.
 * <p>
 * All custom exceptions should extend this class and provide
 * an appropriate HTTP status code and error code.
 * </p>
 */
public abstract class BaseException extends RuntimeException {

    private final String errorCode;
    private final int httpStatus;

    /**
     * Constructs a new {@code BaseException} with a message, error code, and HTTP status.
     *
     * @param message    a human-readable description of the error
     * @param errorCode  a machine-readable error code
     * @param httpStatus the HTTP status code associated with this exception
     */
    protected BaseException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a new {@code BaseException} with a message, cause, error code, and HTTP status.
     *
     * @param message    a human-readable description of the error
     * @param cause      the underlying cause of this exception
     * @param errorCode  a machine-readable error code
     * @param httpStatus the HTTP status code associated with this exception
     */
    protected BaseException(String message, Throwable cause, String errorCode, int httpStatus) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    /**
     * Returns the machine-readable error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HTTP status code
     */
    public int getHttpStatus() {
        return httpStatus;
    }
}
