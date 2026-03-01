# Phase 2 — GitHub Copilot Agentic SDLC Workflow Proposal

## Overview

This document describes the end-to-end agentic Software Development Life Cycle (SDLC) pipeline
powered by **GitHub Copilot agents** across the multi-repository Java ecosystem composed of:

| Repository | Role |
|---|---|
| `vinipx/common-library` | Shared Java utility library (DTOs, exceptions, helpers) |
| `vinipx/service-alpha` | Spring Boot RESTful service A, depends on `common-library` |
| `vinipx/service-beta` | Spring Boot RESTful service B, depends on `common-library` |

The pipeline spans five stages, each executed autonomously by GitHub Copilot agents with
human-in-the-loop approval gates at critical checkpoints.

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        GitHub Copilot Agentic Pipeline                      │
│                                                                             │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌───────┐│
│  │  Stage 1 │───▶│  Stage 2 │───▶│  Stage 3 │───▶│  Stage 4 │───▶│Stage 5││
│  │  JIRA    │    │  Impact  │    │   Code   │    │   Test   │    │  PR   ││
│  │ Ingest   │    │ Analysis │    │   Impl   │    │  AutoGen │    │ Create││
│  └──────────┘    └──────────┘    └──────────┘    └──────────┘    └───────┘│
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Stage 1 — Requirement Ingestion from Jira

### Objective
Fetch and interpret a Jira User Story and transform it into a structured, machine-readable
specification that downstream agents can consume.

### Inputs
- Jira issue key (e.g., `PROJ-42`) — provided manually or via a GitHub Actions trigger.

### Integration Mechanism
1. **GitHub–Jira Integration**: Install the official [GitHub for Jira](https://github.com/atlassian/github-for-jira)
   app on the GitHub organization. This app enables bidirectional linking between Jira issues and
   GitHub pull requests, branches, and commits.
2. **Jira REST API call**: A GitHub Actions workflow step authenticates with the Jira REST API
   (`GET /rest/api/3/issue/{issueKey}`) using a stored GitHub Actions secret (`JIRA_API_TOKEN`)
   and retrieves the full issue payload (summary, description, acceptance criteria, story points).
3. **Structured parsing**: A Copilot agent step reads the raw Jira JSON and outputs a normalized
   `implementation-spec.json` artifact containing:
   - `title`, `description`, `acceptanceCriteria[]`
   - `affectedDomains[]` (e.g., `["user-management", "authentication"]`)
   - `priority`, `storyPoints`

### GitHub Actions Trigger

```yaml
# .github/workflows/agentic-pipeline.yml
on:
  workflow_dispatch:
    inputs:
      jira_issue_key:
        description: 'Jira issue key (e.g. PROJ-42)'
        required: true
```

### Outputs
- `implementation-spec.json` — structured feature specification artifact.

---

## Stage 2 — Cross-Repository Impact Analysis

### Objective
Analyze the implementation spec to determine which files and layers across all three repositories
are affected, then produce a structured **Implementation Plan**.

### Process
1. **Copilot agent context loading**: The agent is given the `implementation-spec.json` plus the
   `copilot-instructions.md` and `java-coding-guidelines.md` from each repository as context.
2. **Blast-radius analysis**: The agent inspects the public API surface of `common-library` and the
   controller/service/repository layer signatures in `service-alpha` and `service-beta` to identify:
   - New or changed DTOs in `common-library`.
   - New or changed endpoints in `service-alpha` / `service-beta`.
   - Database schema changes (if any).
   - Cross-service contract impacts.
3. **Implementation Plan generation**: The agent outputs a `implementation-plan.md` containing:
   - Per-repository task list with file paths, class names, and method signatures to create/modify.
   - Dependency order (e.g., `common-library` changes must be implemented before service changes).
   - Risk flags (e.g., breaking API changes requiring version bump).

### Outputs
- `implementation-plan.md` — ranked, ordered task list per repository.

---

## Stage 3 — Code Implementation

### Objective
Execute the implementation plan by writing production-quality Java code across all impacted
repositories, strictly following `java-coding-guidelines.md`.

### Process
1. **Branch creation**: The agent creates a feature branch in each impacted repository using the
   naming convention `feature/PROJ-42-<short-description>`.
2. **Sequential implementation** (dependency order respected):
   a. `common-library` — new/updated DTOs (Java records), utility methods, or custom exceptions.
   b. `service-alpha` — new/updated controllers, services, repositories, and error handlers.
   c. `service-beta` — same as above where impacted.
3. **Coding standards enforcement**: Every generated file must:
   - Carry full Javadoc on all public classes and methods (`@param`, `@return`, `@throws`).
   - Use constructor injection only (no field-level `@Autowired`).
   - Follow the layered architecture: `Controller → Service → Repository`.
   - Return `ResponseEntity<ApiResponse<T>>` from all REST controllers.
   - Use `BaseException` subclasses for all error conditions.
4. **Self-review loop**: After each file is written, the agent re-reads it against the guidelines
   checklist and applies corrections before committing.

### Guardrails
- The agent MUST NOT remove or rename public methods in `common-library` without adding a
  `@Deprecated` annotation and a migration note.
- The agent MUST NOT add Spring Framework dependencies to `common-library`.
- Maximum file length: 300 lines; maximum method length: 30 lines (per guidelines).

### Outputs
- Feature branch commits in each affected repository.

---

## Stage 4 — Test Automation

### Objective
Generate comprehensive unit and integration tests for all new or modified code to meet or exceed
the coverage thresholds defined in `java-coding-guidelines.md`.

### Coverage Targets

| Repository | Target |
|---|---|
| `common-library` | ≥ 90% line coverage |
| `service-alpha` | ≥ 80% line coverage |
| `service-beta` | ≥ 80% line coverage |

### Process
1. **Unit tests** — For every new class, the agent generates a corresponding `*Test.java` class
   following the AAA (Arrange → Act → Assert) pattern with descriptive names:
   `shouldDoX_whenConditionY()`.
   - Happy-path scenarios.
   - Edge cases (null inputs, empty collections, boundary values).
   - Error/exception scenarios.
2. **Integration tests** — For each new REST endpoint, the agent generates a
   `*IntegrationTest.java` using `@SpringBootTest` with `MockMvc` to exercise the full request
   lifecycle from HTTP through to the (mocked) repository layer.
3. **Coverage verification**: JaCoCo is configured in each service's `pom.xml` with a
   `check` goal that fails the build if coverage falls below the target threshold.
4. **Test execution**: The CI pipeline (`ci.yml`) runs `mvn clean verify` and uploads the JaCoCo
   HTML report as a build artifact.

### Outputs
- Test source files committed to each feature branch.
- JaCoCo coverage reports published as GitHub Actions artifacts.

---

## Stage 5 — Pull Request Automation

### Objective
Bundle all changes into Pull Requests, link them to the originating Jira story, and route them to
the mandatory code owner for review and approval.

### Process
1. **PR creation per repository**: For each repository that received commits, the agent calls the
   GitHub REST API (`POST /repos/{owner}/{repo}/pulls`) to open a Pull Request from the feature
   branch to `main`, populating:
   - **Title**: `feat(PROJ-42): <summary from implementation spec>`
   - **Body**: Structured PR description including:
     - Jira story link (`PROJ-42`)
     - Summary of changes
     - Test coverage results
     - Checklist (tests pass, Javadoc added, guidelines followed)
   - **Labels**: `feature`, `agentic`
2. **Reviewer assignment via CODEOWNERS**: Each repository's `.github/CODEOWNERS` file assigns
   `@vinipx` as the mandatory reviewer for all changes (`* @vinipx`). GitHub automatically
   requests a review from `@vinipx` when a PR is opened.
3. **Cross-PR linking**: Each PR body references the sibling PRs in the other repositories
   to give reviewers full cross-repository context.
4. **Jira ticket update**: The GitHub–Jira integration automatically transitions the Jira issue to
   `In Review` and links all opened PRs to the original story.

### Human Gate
The pipeline pauses after PR creation. `@vinipx` reviews each PR and either:
- **Approves** → the merge is performed by the agent (or manually).
- **Requests changes** → the agent reads the review comments, applies fixes, and force-pushes to
  the same feature branch, re-triggering CI.

### Outputs
- Pull Requests open in `common-library`, `service-alpha`, and/or `service-beta`.
- Review request assigned to `@vinipx` via CODEOWNERS.
- Jira issue transitioned to `In Review`.

---

## End-to-End Sequence

```
Developer / PM
     │
     │  1. Opens Jira story PROJ-42
     │
     ▼
GitHub Actions (manual dispatch or Jira webhook)
     │
     │  2. Fetches Jira issue via REST API → implementation-spec.json
     │
     ▼
Copilot Agent — Impact Analysis
     │
     │  3. Reads copilot-instructions.md + java-coding-guidelines.md
     │     Produces implementation-plan.md
     │
     ▼
Copilot Agent — Code Implementation
     │
     │  4. Creates feature branches
     │     Implements code in dependency order:
     │     common-library → service-alpha → service-beta
     │
     ▼
Copilot Agent — Test Generation
     │
     │  5. Writes unit + integration tests
     │     Verifies JaCoCo coverage thresholds
     │
     ▼
Copilot Agent — PR Creation
     │
     │  6. Opens PRs in each repo
     │     Assigns @vinipx as reviewer (via CODEOWNERS)
     │     Links Jira story to PRs
     │
     ▼
@vinipx (Code Owner)
     │
     │  7. Reviews PRs, approves or requests changes
     │
     ▼
Merge & Deploy
```

---

## GitHub Actions Pipeline Skeleton

```yaml
# .github/workflows/agentic-pipeline.yml
name: Agentic SDLC Pipeline

on:
  workflow_dispatch:
    inputs:
      jira_issue_key:
        description: 'Jira issue key (e.g. PROJ-42)'
        required: true

jobs:
  ingest-requirements:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Fetch Jira Story
        env:
          JIRA_BASE_URL: ${{ secrets.JIRA_BASE_URL }}
          JIRA_API_TOKEN: ${{ secrets.JIRA_API_TOKEN }}
          JIRA_USER_EMAIL: ${{ secrets.JIRA_USER_EMAIL }}
        run: |
          curl -u "$JIRA_USER_EMAIL:$JIRA_API_TOKEN" \
            "$JIRA_BASE_URL/rest/api/3/issue/${{ inputs.jira_issue_key }}" \
            -o implementation-spec.json
      - uses: actions/upload-artifact@v4
        with:
          name: implementation-spec
          path: implementation-spec.json

  analyze-impact:
    needs: ingest-requirements
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/download-artifact@v4
        with:
          name: implementation-spec
      - name: Run Copilot Impact Analysis
        uses: github/copilot-agent-action@v1   # placeholder — use actual action when available
        with:
          prompt: |
            Read implementation-spec.json and the copilot-instructions.md from each repository.
            Produce implementation-plan.md with a ranked task list per repository.

  implement-code:
    needs: analyze-impact
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Run Copilot Code Implementation
        uses: github/copilot-agent-action@v1
        with:
          prompt: |
            Follow implementation-plan.md and java-coding-guidelines.md.
            Create feature branches and implement all changes.

  generate-tests:
    needs: implement-code
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Run Copilot Test Generation
        uses: github/copilot-agent-action@v1
        with:
          prompt: |
            Generate unit and integration tests for all new or modified code.
            Ensure coverage meets the thresholds in java-coding-guidelines.md.

  create-pull-requests:
    needs: generate-tests
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Open Pull Requests
        env:
          GH_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        run: |
          gh pr create \
            --title "feat(${{ inputs.jira_issue_key }}): implement story" \
            --body "Resolves ${{ inputs.jira_issue_key }}" \
            --reviewer vinipx
```

---

## Required Secrets

| Secret Name | Description |
|---|---|
| `JIRA_BASE_URL` | Base URL of the Jira instance (e.g., `https://myorg.atlassian.net`) |
| `JIRA_API_TOKEN` | Jira API token for authentication |
| `JIRA_USER_EMAIL` | Email address associated with the Jira API token |
| `GITHUB_TOKEN` | Automatically provided by GitHub Actions for PR creation |

---

## Summary

This proposal demonstrates a fully agentic SDLC loop in which GitHub Copilot agents handle every
phase from requirement ingestion to PR creation. Human oversight is preserved through the mandatory
CODEOWNERS review gate assigned to `@vinipx`, ensuring that all AI-generated changes receive
expert validation before being merged into `main`.
