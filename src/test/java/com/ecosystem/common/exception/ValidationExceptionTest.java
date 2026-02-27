package com.ecosystem.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValidationException}.
 */
class ValidationExceptionTest {

    @Test
    void shouldReturnHttpStatus422_whenExceptionCreated() {
        // Act
        ValidationException ex = new ValidationException("Invalid input");

        // Assert
        assertEquals(422, ex.getHttpStatus());
    }

    @Test
    void shouldReturnErrorCodeValidationError_whenExceptionCreated() {
        // Act
        ValidationException ex = new ValidationException("Bad value");

        // Assert
        assertEquals("VALIDATION_ERROR", ex.getErrorCode());
    }

    @Test
    void shouldReturnMessage_whenExceptionCreatedWithMessage() {
        // Arrange
        String message = "Field 'email' is required";

        // Act
        ValidationException ex = new ValidationException(message);

        // Assert
        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldReturnCause_whenExceptionCreatedWithCause() {
        // Arrange
        Throwable cause = new IllegalArgumentException("bad arg");

        // Act
        ValidationException ex = new ValidationException("Validation failed", cause);

        // Assert
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldExtendBaseException_whenCreated() {
        // Act
        ValidationException ex = new ValidationException("test");

        // Assert
        assertInstanceOf(BaseException.class, ex);
        assertInstanceOf(RuntimeException.class, ex);
    }
}
