# common-library

A shared Java 21 utility library consumed by `service-alpha` and `service-beta` in the ecosystem.
It provides common DTOs, custom exceptions, and utility classes used across services.

## Prerequisites

- Java 21+
- Maven 3.9+

## Build

```bash
mvn clean install
```

## Run Tests

```bash
mvn clean verify
```

## Package Structure

```
com.ecosystem.common
├── dto
│   ├── ApiResponse     — Generic API response wrapper (Java record)
│   └── UserDto         — Shared User DTO (Java record)
├── exception
│   ├── BaseException   — Abstract base exception with errorCode and httpStatus
│   ├── NotFoundException   — 404 exception
│   ├── ValidationException — 422 exception
│   └── ConflictException   — 409 exception
└── util
    ├── StringUtils     — isBlank, truncate, capitalize
    └── DateUtils       — formatIso, parseIso, isExpired
```

## Using as a Dependency

Add the following to your `pom.xml`:

```xml
<dependency>
    <groupId>com.ecosystem</groupId>
    <artifactId>common-library</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Then install it to your local Maven repository:

```bash
mvn clean install
```
