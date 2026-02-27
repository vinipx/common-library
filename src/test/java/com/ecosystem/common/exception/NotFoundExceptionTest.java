package com.ecosystem.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link NotFoundException}.
 */
class NotFoundExceptionTest {

    @Test
    void shouldReturnHttpStatus404_whenExceptionCreated() {
        // Act
        NotFoundException ex = new NotFoundException("User not found");

        // Assert
        assertEquals(404, ex.getHttpStatus());
    }

    @Test
    void shouldReturnErrorCodeNotFound_whenExceptionCreated() {
        // Act
        NotFoundException ex = new NotFoundException("Resource missing");

        // Assert
        assertEquals("NOT_FOUND", ex.getErrorCode());
    }

    @Test
    void shouldReturnMessage_whenExceptionCreatedWithMessage() {
        // Arrange
        String message = "Item with id 42 not found";

        // Act
        NotFoundException ex = new NotFoundException(message);

        // Assert
        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldReturnCause_whenExceptionCreatedWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("underlying cause");

        // Act
        NotFoundException ex = new NotFoundException("Not found", cause);

        // Assert
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldExtendBaseException_whenCreated() {
        // Act
        NotFoundException ex = new NotFoundException("test");

        // Assert
        assertInstanceOf(BaseException.class, ex);
        assertInstanceOf(RuntimeException.class, ex);
    }
}
