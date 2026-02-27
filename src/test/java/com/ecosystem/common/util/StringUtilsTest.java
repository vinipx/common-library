package com.ecosystem.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StringUtils}.
 */
class StringUtilsTest {

    // --- isBlank ---

    @Test
    void shouldReturnTrue_whenIsBlankCalledWithNull() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    void shouldReturnTrue_whenIsBlankCalledWithEmptyString() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    void shouldReturnTrue_whenIsBlankCalledWithWhitespace() {
        assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    void shouldReturnFalse_whenIsBlankCalledWithNonBlankString() {
        assertFalse(StringUtils.isBlank("hello"));
    }

    @Test
    void shouldReturnFalse_whenIsBlankCalledWithStringContainingNonWhitespace() {
        assertFalse(StringUtils.isBlank("  a  "));
    }

    // --- truncate ---

    @Test
    void shouldTruncateString_whenLongerThanMaxLength() {
        assertEquals("hel", StringUtils.truncate("hello", 3));
    }

    @Test
    void shouldReturnOriginal_whenShorterThanMaxLength() {
        assertEquals("hi", StringUtils.truncate("hi", 10));
    }

    @Test
    void shouldReturnOriginal_whenExactlyMaxLength() {
        assertEquals("hello", StringUtils.truncate("hello", 5));
    }

    @Test
    void shouldReturnNull_whenTruncateCalledWithNull() {
        assertNull(StringUtils.truncate(null, 5));
    }

    @Test
    void shouldReturnEmptyString_whenTruncateCalledWithZeroMaxLength() {
        assertEquals("", StringUtils.truncate("hello", 0));
    }

    @Test
    void shouldThrowIllegalArgument_whenMaxLengthIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.truncate("hello", -1));
    }

    // --- capitalize ---

    @Test
    void shouldCapitalizeFirstLetter_whenLowercaseStringProvided() {
        assertEquals("Hello", StringUtils.capitalize("hello"));
    }

    @Test
    void shouldLowercaseRest_whenMixedCaseStringProvided() {
        assertEquals("Hello world", StringUtils.capitalize("HELLO WORLD"));
    }

    @Test
    void shouldReturnNull_whenCapitalizeCalledWithNull() {
        assertNull(StringUtils.capitalize(null));
    }

    @Test
    void shouldReturnEmpty_whenCapitalizeCalledWithEmptyString() {
        assertEquals("", StringUtils.capitalize(""));
    }

    @Test
    void shouldCapitalizeSingleChar_whenSingleCharProvided() {
        assertEquals("A", StringUtils.capitalize("a"));
    }

    @Test
    void shouldThrowException_whenInstantiationAttempted() {
        // StringUtils constructor is private; verify via reflection
        assertThrows(Exception.class, () -> {
            var constructor = StringUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
}
