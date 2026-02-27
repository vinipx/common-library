# Java Coding Guidelines

## 1. Language & Platform

- **Java 21** is the minimum supported version across all services and libraries.
- Target and source compatibility must be set to Java 21 in `pom.xml`.

---

## 2. Naming Conventions

| Element | Convention | Example |
|---|---|---|
| Classes / Interfaces | PascalCase | `UserService`, `ApiResponse` |
| Methods | camelCase | `getUserById`, `formatIso` |
| Variables | camelCase | `userId`, `createdAt` |
| Constants | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT` |
| Packages | lowercase, dot-separated | `com.ecosystem.common.util` |

---

## 3. Code Organization

- **Maximum 300 lines per file** (excluding blank lines and comments).
- **Maximum 30 lines per method** (excluding blank lines and comments).
- **Maximum 4 parameters per method**. Use parameter objects or builders for more complex signatures.
- One top-level public class per file, with the filename matching the class name.
- Group class members: constants → fields → constructors → public methods → private methods.

---

## 4. Architecture

### Layered Pattern for Services
```
Controller  →  Service  →  Repository
```
- **Controller**: Handles HTTP request/response, delegates to service.
- **Service**: Orchestrates business logic, calls repositories.
- **Repository**: Data access only; no business logic.

### Dependency Direction
- Always depend inward: outer layers depend on inner layers, never the reverse.
- `common-library` has no dependency on any service.

---

## 5. Dependency Injection

- **Constructor injection only** — never use field injection (`@Autowired` on fields).
- Mark injected constructor parameters as `final`.
- Example:

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

---

## 6. REST API Standards

- Use **plural nouns** for resource paths: `/users`, `/orders`.
- All endpoints are prefixed with `/api/v1/`.
- Return `ResponseEntity<ApiResponse<T>>` from controllers.
- Use standard HTTP status codes: 200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found, 409 Conflict, 422 Unprocessable Entity, 500 Internal Server Error.

---

## 7. Exception Handling

- All custom exceptions **must extend `BaseException`** (from `common-library`).
- Use specific exception types:
  - `NotFoundException` → 404
  - `ValidationException` → 422
  - `ConflictException` → 409
- Use a global `@ControllerAdvice` to handle exceptions and return consistent `ApiResponse` error responses.
- Do **not** catch and swallow exceptions silently.

---

## 8. Testing Standards

| Scope | Minimum Coverage |
|---|---|
| `common-library` | ≥ 90% |
| Services (`service-alpha`, `service-beta`) | ≥ 80% |

- Use **JUnit 5** (`junit-jupiter`) for all tests.
- Follow the **AAA pattern**: Arrange → Act → Assert.
- Use descriptive method names: `shouldDoX_whenConditionY()`.
- Test both happy-path and error cases.
- Use JaCoCo for coverage reporting; configure the CI pipeline to publish the report.

---

## 9. Javadoc

- All **public** classes, interfaces, methods, and record components must have Javadoc.
- Javadoc must describe *what* the element does, not *how*.
- Include `@param`, `@return`, and `@throws` tags where applicable.
- Private/package-private members do not require Javadoc but should have inline comments when the logic is non-trivial.

---

## 10. Git Standards

### Conventional Commits

Use the following commit message format:

```
<type>(<scope>): <short description>

[optional body]
[optional footer]
```

Types: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`, `ci`.

Examples:
- `feat(user): add endpoint to retrieve user by email`
- `fix(auth): handle expired token gracefully`
- `docs(readme): update build instructions`

### Branch Naming

```
feature/JIRA-XXX-short-description
bugfix/JIRA-XXX-short-description
chore/JIRA-XXX-short-description
```

Example: `feature/PROJ-42-add-user-search`

---

## 11. Utility Classes

- Utility classes must have a **private constructor** and throw `UnsupportedOperationException`.
- All methods must be **static**.

```java
public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    // static methods...
}
```

---

## 12. DTOs and Records

- Prefer **Java records** for immutable data transfer objects.
- Do not add business logic inside DTOs.
- Validate inputs at the service layer, not in the DTO.
