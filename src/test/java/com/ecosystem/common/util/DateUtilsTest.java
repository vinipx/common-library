package com.ecosystem.common.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DateUtils}.
 */
class DateUtilsTest {

    // --- formatIso ---

    @Test
    void shouldReturnIsoString_whenFormatIsoCalledWithInstant() {
        // Arrange
        Instant instant = Instant.parse("2024-01-15T10:30:00Z");

        // Act
        String result = DateUtils.formatIso(instant);

        // Assert
        assertEquals("2024-01-15T10:30:00Z", result);
    }

    @Test
    void shouldThrowNullPointer_whenFormatIsoCalledWithNull() {
        assertThrows(NullPointerException.class, () -> DateUtils.formatIso(null));
    }

    // --- parseIso ---

    @Test
    void shouldReturnInstant_whenParseIsoCalledWithValidString() {
        // Arrange
        String isoString = "2024-01-15T10:30:00Z";

        // Act
        Instant result = DateUtils.parseIso(isoString);

        // Assert
        assertEquals(Instant.parse(isoString), result);
    }

    @Test
    void shouldThrowNullPointer_whenParseIsoCalledWithNull() {
        assertThrows(NullPointerException.class, () -> DateUtils.parseIso(null));
    }

    @Test
    void shouldThrowDateTimeParseException_whenParseIsoCalledWithInvalidString() {
        assertThrows(DateTimeParseException.class, () -> DateUtils.parseIso("not-a-date"));
    }

    @Test
    void shouldBeSymmetric_whenFormatAndParseRoundTrip() {
        // Arrange
        Instant original = Instant.parse("2024-06-01T12:00:00Z");

        // Act
        String formatted = DateUtils.formatIso(original);
        Instant parsed = DateUtils.parseIso(formatted);

        // Assert
        assertEquals(original, parsed);
    }

    // --- isExpired ---

    @Test
    void shouldReturnTrue_whenInstantIsInThePast() {
        // Arrange
        Instant pastInstant = Instant.now().minusSeconds(3600);

        // Act
        boolean result = DateUtils.isExpired(pastInstant);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenInstantIsInTheFuture() {
        // Arrange
        Instant futureInstant = Instant.now().plusSeconds(3600);

        // Act
        boolean result = DateUtils.isExpired(futureInstant);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldThrowNullPointer_whenIsExpiredCalledWithNull() {
        assertThrows(NullPointerException.class, () -> DateUtils.isExpired(null));
    }

    @Test
    void shouldThrowException_whenInstantiationAttempted() {
        // DateUtils constructor is private; verify via reflection
        assertThrows(Exception.class, () -> {
            var constructor = DateUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
}
