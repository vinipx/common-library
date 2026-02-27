package com.ecosystem.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ConflictException}.
 */
class ConflictExceptionTest {

    @Test
    void shouldReturnHttpStatus409_whenExceptionCreated() {
        // Act
        ConflictException ex = new ConflictException("Resource already exists");

        // Assert
        assertEquals(409, ex.getHttpStatus());
    }

    @Test
    void shouldReturnErrorCodeConflict_whenExceptionCreated() {
        // Act
        ConflictException ex = new ConflictException("Duplicate entry");

        // Assert
        assertEquals("CONFLICT", ex.getErrorCode());
    }

    @Test
    void shouldReturnMessage_whenExceptionCreatedWithMessage() {
        // Arrange
        String message = "User with email already registered";

        // Act
        ConflictException ex = new ConflictException(message);

        // Assert
        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldReturnCause_whenExceptionCreatedWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("db constraint violation");

        // Act
        ConflictException ex = new ConflictException("Conflict", cause);

        // Assert
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldExtendBaseException_whenCreated() {
        // Act
        ConflictException ex = new ConflictException("test");

        // Assert
        assertInstanceOf(BaseException.class, ex);
        assertInstanceOf(RuntimeException.class, ex);
    }
}
