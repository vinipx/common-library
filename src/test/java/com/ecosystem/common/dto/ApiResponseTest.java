package com.ecosystem.common.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ApiResponse}.
 */
class ApiResponseTest {

    @Test
    void shouldReturnSuccess_whenOkCalledWithData() {
        // Arrange
        String data = "test-payload";

        // Act
        ApiResponse<String> response = ApiResponse.ok(data);

        // Assert
        assertTrue(response.success());
        assertEquals("Success", response.message());
        assertEquals(data, response.data());
        assertNotNull(response.timestamp());
    }

    @Test
    void shouldReturnFalseSuccess_whenErrorCalled() {
        // Arrange
        String errorMessage = "Something went wrong";

        // Act
        ApiResponse<Object> response = ApiResponse.error(errorMessage);

        // Assert
        assertFalse(response.success());
        assertEquals(errorMessage, response.message());
        assertNull(response.data());
        assertNotNull(response.timestamp());
    }

    @Test
    void shouldReturnNullData_whenErrorCalled() {
        // Act
        ApiResponse<String> response = ApiResponse.error("error");

        // Assert
        assertNull(response.data());
    }

    @Test
    void shouldSetTimestampCloseToNow_whenOkCalled() {
        // Arrange
        Instant before = Instant.now().minusSeconds(1);

        // Act
        ApiResponse<String> response = ApiResponse.ok("data");

        // Assert
        Instant after = Instant.now().plusSeconds(1);
        assertTrue(response.timestamp().isAfter(before));
        assertTrue(response.timestamp().isBefore(after));
    }

    @Test
    void shouldSetTimestampCloseToNow_whenErrorCalled() {
        // Arrange
        Instant before = Instant.now().minusSeconds(1);

        // Act
        ApiResponse<String> response = ApiResponse.error("error");

        // Assert
        Instant after = Instant.now().plusSeconds(1);
        assertTrue(response.timestamp().isAfter(before));
        assertTrue(response.timestamp().isBefore(after));
    }

    @Test
    void shouldSupportNullData_whenOkCalledWithNull() {
        // Act
        ApiResponse<String> response = ApiResponse.ok(null);

        // Assert
        assertTrue(response.success());
        assertNull(response.data());
    }

    @Test
    void shouldConstructDirectly_whenAllFieldsProvided() {
        // Arrange
        Instant now = Instant.now();

        // Act
        ApiResponse<Integer> response = new ApiResponse<>(true, "msg", 42, now);

        // Assert
        assertTrue(response.success());
        assertEquals("msg", response.message());
        assertEquals(42, response.data());
        assertEquals(now, response.timestamp());
    }
}
