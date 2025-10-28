# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin library providing time extensions for kotlin stdlib, Exposed ORM, and serialization. It's published to Maven Central as `com.turbomates:time`.

## Build System

- **Build Tool**: Gradle with Kotlin DSL
- **JVM Target**: Java 21
- **Kotlin Version**: 2.2.20 (configured in settings.gradle.kts)

## Common Commands

### Building and Testing
- Build: `./gradlew build` or `make gradlew-build` (alias: `make gb`)
- Run tests: `./gradlew test` or `make test` (alias: `make t`)
- View all tasks: `./gradlew tasks` or `make gradlew-tasks` (alias: `make gt`)

### Running Single Tests
- Run specific test class: `./gradlew test --tests "com.turbomates.time.period.OffsetDateTimeTest"`
- Run specific test method: `./gradlew test --tests "com.turbomates.time.period.OffsetDateTimeTest.testMethodName"`

### Code Quality (Detekt)
- Run all detekt checks: `make detekt` (alias: `make d`)
- Main sources only: `./gradlew detektMain` or `make detekt-main` (alias: `make dm`)
- Test sources only: `./gradlew detektTest` or `make detekt-test` (alias: `make dt`)
- Update baseline for main: `./gradlew detektBaselineMain` or `make detekt-baseline-main` (alias: `make dbm`)
- Update baseline for test: `./gradlew detektBaselineTest` or `make detekt-baseline-test` (alias: `make dbt`)

Note: Detekt is configured in `detekt.yml` with baselines in `detekt-baseline-main.xml` and `detekt-baseline-test.xml`

## Code Architecture

### Package Structure
- `com.turbomates.time`: Core time extensions for Java Time API (Date, LocalDate, LocalDateTime, OffsetDateTime)
- `com.turbomates.time.period`: Period abstractions with custom serialization (e.g., "7d", "3m")
- `com.turbomates.time.exposed`: Custom Exposed ORM column types for UTC datetime handling
- `com.turbomates.time.valiktor`: Validation rules for Period objects

### Key Components

**Period System** (`src/main/kotlin/com/turbomates/time/period/`)
- `Period`: A data class representing time periods with number and type (hours/days/weeks/months/years)
- Supports string parsing (e.g., "7d", "3m") and serialization via `PeriodSerializer`
- Can convert periods to date/datetime ranges
- Extensions in `Extensions.kt` add `minusPeriod()` and `startOf()` functions to date/time types

**Exposed Integration** (`src/main/kotlin/com/turbomates/time/exposed/`)
- `UTCDateTimeColumn`: Custom Exposed column type that stores OffsetDateTime values in UTC
- `datetime(name: String)`: Extension function to register UTC datetime columns in Exposed tables
- `CurrentTimestamp`: SQL function for getting current timestamp
- All datetime values are normalized to UTC when stored/retrieved from the database

**Core Extensions**
- Extension functions on Java Time API types (OffsetDateTime, LocalDate, LocalDateTime, etc.)
- `DateTimeRange`: Simple data class for representing datetime ranges
- `Format.kt`: Formatting utilities for time types

### Important Configuration Details

**Compiler Options** (build.gradle.kts:48-54)
- Context receivers enabled with `-Xcontext-receivers`
- Opt-in annotations for serialization APIs
- JVM target is Java 21

**Dependencies**
- Uses version catalog in `settings.gradle.kts` (accessed via `deps` in build.gradle.kts)
- Key dependencies: Ktor (3.3.0), Exposed (1.0.0-rc-2), Kotest (6.0.0), Valiktor (0.12.0)
- Serialization with kotlinx-serialization-json

**Publishing**
- Published to Maven Central via Sonatype OSSRH
- Requires `RELEASE_VERSION` environment variable for versioning (defaults to "0.1.0")
- Credentials via `ORG_GRADLE_PROJECT_SONATYPE_USERNAME` and `ORG_GRADLE_PROJECT_SONATYPE_PASSWORD` environment variables