package com.ecosystem.common.util;

/**
 * Utility class for common string operations.
 * <p>
 * All methods are static. This class cannot be instantiated.
 * </p>
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Checks whether the given string is {@code null} or contains only whitespace characters.
     *
     * @param value the string to check
     * @return {@code true} if the string is null or blank, {@code false} otherwise
     */
    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    /**
     * Truncates the given string to at most {@code maxLength} characters.
     * If the string is shorter than or equal to {@code maxLength}, it is returned unchanged.
     *
     * @param value     the string to truncate
     * @param maxLength the maximum number of characters to keep
     * @return the truncated string, or the original string if it fits within {@code maxLength}
     * @throws IllegalArgumentException if {@code maxLength} is negative
     */
    public static String truncate(String value, int maxLength) {
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength must not be negative");
        }
        if (value == null) {
            return null;
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }

    /**
     * Capitalizes the first character of the given string and lowercases the rest.
     * Returns {@code null} if the input is {@code null}, and an empty string if the input
     * is empty.
     *
     * @param value the string to capitalize
     * @return the capitalized string, or {@code null} if the input is {@code null}
     */
    public static String capitalize(String value) {
        if (value == null) {
            return null;
        }
        if (value.isEmpty()) {
            return value;
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1).toLowerCase();
    }
}
