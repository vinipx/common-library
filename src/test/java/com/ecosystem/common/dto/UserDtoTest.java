package com.ecosystem.common.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserDto}.
 */
class UserDtoTest {

    @Test
    void shouldCreateUserDto_whenAllFieldsProvided() {
        // Arrange
        Instant now = Instant.now();

        // Act
        UserDto user = new UserDto(1L, "jdoe", "jdoe@example.com", "John Doe", now, now);

        // Assert
        assertEquals(1L, user.id());
        assertEquals("jdoe", user.username());
        assertEquals("jdoe@example.com", user.email());
        assertEquals("John Doe", user.fullName());
        assertEquals(now, user.createdAt());
        assertEquals(now, user.updatedAt());
    }

    @Test
    void shouldSupportNullFields_whenOptionalFieldsMissing() {
        // Act
        UserDto user = new UserDto(null, null, null, null, null, null);

        // Assert
        assertNull(user.id());
        assertNull(user.username());
        assertNull(user.email());
        assertNull(user.fullName());
        assertNull(user.createdAt());
        assertNull(user.updatedAt());
    }

    @Test
    void shouldBeEqualToAnotherUserDto_whenSameFieldsProvided() {
        // Arrange
        Instant now = Instant.now();
        UserDto user1 = new UserDto(1L, "jdoe", "jdoe@example.com", "John Doe", now, now);
        UserDto user2 = new UserDto(1L, "jdoe", "jdoe@example.com", "John Doe", now, now);

        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void shouldNotBeEqual_whenDifferentIds() {
        // Arrange
        Instant now = Instant.now();
        UserDto user1 = new UserDto(1L, "jdoe", "jdoe@example.com", "John Doe", now, now);
        UserDto user2 = new UserDto(2L, "jdoe", "jdoe@example.com", "John Doe", now, now);

        // Assert
        assertNotEquals(user1, user2);
    }

    @Test
    void shouldReturnStringRepresentation_whenToStringCalled() {
        // Arrange
        Instant now = Instant.now();
        UserDto user = new UserDto(1L, "jdoe", "jdoe@example.com", "John Doe", now, now);

        // Act
        String result = user.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("jdoe"));
    }
}
