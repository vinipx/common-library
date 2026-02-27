# Common Library — Copilot Agent Instructions

## Repository Purpose
This is a shared Java utility library (`com.ecosystem.common`) consumed by both `service-alpha` and `service-beta`. It contains DTOs, utility classes, custom exceptions, and cross-cutting concerns.

## Architecture
- **Language**: Java 21
- **Build Tool**: Maven 3.9+
- **Packaging**: JAR (published to local Maven repo or GitHub Packages)
- **No Spring Boot dependency** — this is a plain Java library

## Package Structure
```
com.ecosystem.common.dto        → Shared Data Transfer Objects
com.ecosystem.common.util       → Utility/helper classes (StringUtils, DateUtils, etc.)
com.ecosystem.common.exception  → Custom exception hierarchy (BaseException, NotFoundException, etc.)
```

## Rules for Agents
1. Backward compatibility is critical — never remove or rename public methods without deprecation.
2. All public classes and methods MUST have Javadoc.
3. All utility methods MUST be static and classes MUST have a private constructor.
4. DTOs MUST use Java records where possible.
5. Custom exceptions MUST extend BaseException.
6. Minimum 90% unit test coverage for this library.
7. Do NOT add Spring framework dependencies here.
