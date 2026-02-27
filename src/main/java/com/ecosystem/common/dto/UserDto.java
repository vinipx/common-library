package com.ecosystem.common.dto;

import java.time.Instant;

/**
 * Shared User Data Transfer Object used across services.
 *
 * @param id the unique identifier of the user
 * @param username the login username
 * @param email the user's email address
 * @param fullName the user's full display name
 * @param createdAt the timestamp when the user was created
 * @param updatedAt the timestamp when the user was last updated
 */
public record UserDto(Long id, String username, String email, String fullName,
                      Instant createdAt, Instant updatedAt) {
}
