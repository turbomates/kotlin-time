package com.turbomates.time.period

import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals

class OffsetDateTimeTest {
    @Test
    fun `start of week`() {
        assertEquals(
            OffsetDateTime.of(2022, 6, 27, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 1, 10, 0, 0, 0, ZoneOffset.UTC).startOf(PeriodType.WEEKS)
        )

        assertEquals(
            OffsetDateTime.of(2022, 7, 4, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 6, 22, 53, 0, 0, ZoneOffset.UTC).startOf(PeriodType.WEEKS)
        )
    }
    @Test
    fun `end of hour`() {
        assertEquals(
            OffsetDateTime.of(2022, 7, 1, 10, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 1, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.HOURS)
        )
    }

    @Test
    fun `end of day`() {
        assertEquals(
            OffsetDateTime.of(2022, 7, 1, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 1, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.DAYS)
        )
    }

    @Test
    fun `end of week`() {
        assertEquals(
            OffsetDateTime.of(2022, 7, 3, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 1, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.WEEKS)
        )
        assertEquals(
            OffsetDateTime.of(2022, 7, 10, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 6, 22, 53, 0, 0, ZoneOffset.UTC).endOf(PeriodType.WEEKS)
        )
    }

    @Test
    fun `end of month`() {
        assertEquals(
            OffsetDateTime.of(2022, 7, 31, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 1, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.MONTHS)
        )
        assertEquals(
            OffsetDateTime.of(2022, 2, 28, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 2, 10, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.MONTHS)
        )
        assertEquals(
            OffsetDateTime.of(2020, 2, 29, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2020, 2, 10, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.MONTHS)
        )
    }

    @Test
    fun `end of year`() {
        assertEquals(
            OffsetDateTime.of(2022, 12, 31, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 7, 1, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.YEARS)
        )
        assertEquals(
            OffsetDateTime.of(2020, 12, 31, 23, 59, 59, 999_999_999, ZoneOffset.UTC),
            OffsetDateTime.of(2020, 2, 10, 10, 0, 0, 0, ZoneOffset.UTC).endOf(PeriodType.YEARS)
        )
    }
}
