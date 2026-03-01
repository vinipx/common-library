# Copilot Agent Instructions — common-library

## Repository Purpose
`common-library` contains shared Java utilities, helpers, base abstractions, and cross-cutting components consumed by:
- `vinipx/service-alpha`
- `vinipx/service-beta`

## Boundaries & Responsibilities
- Host only reusable, domain-agnostic/shared capabilities.
- Do NOT include service-specific business rules.
- Versioning and backward compatibility are mandatory concerns.

## Tech Stack
- Java 21+
- Maven (publishable artifact)
- JUnit 5

## Agent Operating Instructions
1. Start from Jira context and identify shared concern candidate.
2. Validate necessity of shared abstraction:
   - Is it reused now or expected reusable soon?
3. Design for stable contracts:
   - Clear interfaces
   - Semantic versioning mindset
4. Add JavaDocs for public APIs.
5. Add comprehensive unit tests for all exported behaviors.
6. If breaking changes are unavoidable:
   - Document migration notes
   - Signal major version impact in PR
7. Coordinate consumer updates in `service-alpha` and `service-beta`.

## Definition of Done (Agent)
- Public API documented
- Tests green
- Artifact version updated appropriately
- Consumer impact documented
- PR ready for code owner review
