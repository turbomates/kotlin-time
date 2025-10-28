# Kotlin Time

[![Maven Central](https://img.shields.io/maven-central/v/com.turbomates/time.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.turbomates%22%20AND%20a:%22time%22)
[![Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](https://www.repostatus.org/badges/latest/wip.svg)](https://www.repostatus.org/#wip)
![Build](https://github.com/turbomates/kotlin-time/actions/workflows/tests.yml/badge.svg)
![Detekt](https://github.com/turbomates/kotlin-time/actions/workflows/reviewdog.yml/badge.svg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A Kotlin library providing powerful time extensions for Kotlin stdlib, Exposed ORM, and kotlinx.serialization. Simplify working with dates, times, periods, and time ranges in your Kotlin applications.

## Features

- **Period System**: Intuitive period parsing and manipulation (e.g., "7d", "3m", "2w")
- **Time Extensions**: Rich extension functions for Java Time API types
- **Exposed Integration**: UTC datetime columns with automatic timezone handling
- **Serialization Support**: Built-in kotlinx.serialization support for Period types
- **Validation**: Integration with Valiktor for period validation
- **Type-Safe**: Strongly-typed period operations with compile-time safety

## Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.turbomates:time:0.1.0")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'com.turbomates:time:0.1.0'
}
```

### Maven

```xml
<dependency>
    <groupId>com.turbomates</groupId>
    <artifactId>time</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

### Period System

The Period system provides an intuitive way to work with time periods using simple string notation:

```kotlin
import com.turbomates.time.period.*
import java.time.LocalDate
import java.time.OffsetDateTime

// Parse periods from strings
val sevenDays = Period.fromString("7d")
val threeMonths = Period.fromString("3m")
val twoWeeks = Period.fromString("2w")

// Create date ranges
val dateRange = sevenDays.toDateRange() // Last 7 days until now
val customRange = sevenDays.toDateRange(periodEnd = LocalDate.of(2024, 1, 15))

// Create datetime ranges
val now = OffsetDateTime.now()
val dateTimeRange = threeMonths.toDateTimeRange(now)

// With period boundaries
val rangeWithBoundaries = threeMonths.toDateTimeRange(
    periodEnd = now,
    includeCurrentPeriod = true
)
```

Supported period types:
- `h` - Hours
- `d` - Days
- `w` - Weeks
- `m` - Months
- `y` - Years

### Time Extensions

Rich extension functions for date and time manipulation:

```kotlin
import com.turbomates.time.period.*
import java.time.OffsetDateTime
import java.time.LocalDate

val now = OffsetDateTime.now()
val period = Period(3, PeriodType.MONTHS)

// Add or subtract periods
val future = now.plusPeriod(period)
val past = now.minusPeriod(period)

// Get start/end of periods
val startOfMonth = now.startOf(PeriodType.MONTHS)
val endOfWeek = now.endOf(PeriodType.WEEKS)
val startOfYear = now.startOf(PeriodType.YEARS)

// Works with LocalDate too
val date = LocalDate.now()
val startOfWeek = date.startOf(PeriodType.WEEKS)
val lastMonth = date.minusPeriod(Period(1, PeriodType.MONTHS))
```

### Exposed ORM Integration

Automatically handle UTC datetime columns in Exposed:

```kotlin
import com.turbomates.time.exposed.*
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.statements.insert
import org.jetbrains.exposed.v1.core.transactions.transaction
import java.time.OffsetDateTime

object Users : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}

// Insert with automatic UTC conversion
transaction {
    Users.insert {
        it[email] = "user@example.com"
        it[createdAt] = OffsetDateTime.now() // Automatically converted to UTC
        it[updatedAt] = OffsetDateTime.now()
    }
}

// Use CurrentTimestamp function in queries
transaction {
    Users.insert {
        it[email] = "user@example.com"
        it[createdAt] = CurrentTimestamp()
        it[updatedAt] = CurrentTimestamp()
    }
}
```

All `OffsetDateTime` values are automatically:
- Converted to UTC when stored
- Retrieved as UTC-zoned `OffsetDateTime` instances
- Handled consistently across different database backends

### Serialization

Period types are automatically serializable with kotlinx.serialization:

```kotlin
import com.turbomates.time.period.Period
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class TimeFilter(
    val period: Period,
    val name: String
)

val filter = TimeFilter(
    period = Period.fromString("7d"),
    name = "Last week"
)

// Serialize to JSON
val json = Json.encodeToString(filter)
// {"period":"7 d","name":"Last week"}

// Deserialize from JSON
val decoded = Json.decodeFromString<TimeFilter>(json)
```

## Requirements

- Kotlin 2.2.20 or higher
- Java 21 or higher
- For Exposed integration: Exposed 1.0.0-rc-2 or higher

## Building from Source

```bash
# Clone the repository
git clone https://github.com/turbomates/kotlin-time.git
cd kotlin-time

# Build the project
./gradlew build

# Run tests
./gradlew test

# Run code quality checks
./gradlew detekt
```

### Using Make shortcuts

The project includes convenient Make shortcuts:

```bash
make gb    # Build the project (gradlew build)
make t     # Run tests (test)
make d     # Run detekt checks (detekt)
make dm    # Detekt main sources only
make dt    # Detekt test sources only
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please make sure to:
- Update tests as appropriate
- Run `./gradlew detekt` to ensure code quality
- Follow the existing code style

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Links

- [Maven Central Repository](https://search.maven.org/search?q=g:%22com.turbomates%22%20AND%20a:%22time%22)
- [Issue Tracker](https://github.com/turbomates/kotlin-time/issues)
- [Source Code](https://github.com/turbomates/kotlin-time)

---

Made with ❤️ by [Turbomates](https://turbomates.com)
