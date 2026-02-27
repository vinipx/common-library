package com.ecosystem.common.dto;

import java.time.Instant;

/**
 * Generic API response wrapper for all service responses.
 *
 * @param <T> the type of the response data
 * @param success whether the request was successful
 * @param message a descriptive message about the response
 * @param data the response payload
 * @param timestamp the time the response was generated
 */
public record ApiResponse<T>(boolean success, String message, T data, Instant timestamp) {

    /**
     * Creates a successful response with the given data.
     *
     * @param <T> the type of the response data
     * @param data the response payload
     * @return a successful {@link ApiResponse}
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Success", data, Instant.now());
    }

    /**
     * Creates an error response with the given message.
     *
     * @param <T> the type of the response data
     * @param message the error message
     * @return an error {@link ApiResponse}
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}
