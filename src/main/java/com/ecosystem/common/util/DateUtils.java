package com.ecosystem.common.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for common date and time operations.
 * <p>
 * All methods are static. This class cannot be instantiated.
 * </p>
 */
public final class DateUtils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private DateUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Formats the given {@link Instant} as an ISO-8601 string (e.g., {@code 2024-01-15T10:30:00Z}).
     *
     * @param instant the instant to format; must not be {@code null}
     * @return the ISO-8601 formatted string representation of the instant
     * @throws NullPointerException if {@code instant} is {@code null}
     */
    public static String formatIso(Instant instant) {
        if (instant == null) {
            throw new NullPointerException("instant must not be null");
        }
        return ISO_FORMATTER.format(instant);
    }

    /**
     * Parses an ISO-8601 formatted string into an {@link Instant}.
     *
     * @param value the ISO-8601 date-time string to parse; must not be {@code null}
     * @return the parsed {@link Instant}
     * @throws NullPointerException   if {@code value} is {@code null}
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static Instant parseIso(String value) {
        if (value == null) {
            throw new NullPointerException("value must not be null");
        }
        return Instant.parse(value);
    }

    /**
     * Checks whether the given {@link Instant} is in the past (i.e., before the current time).
     *
     * @param instant the instant to check; must not be {@code null}
     * @return {@code true} if the instant is before {@link Instant#now()}, {@code false} otherwise
     * @throws NullPointerException if {@code instant} is {@code null}
     */
    public static boolean isExpired(Instant instant) {
        if (instant == null) {
            throw new NullPointerException("instant must not be null");
        }
        return instant.isBefore(Instant.now());
    }
}
