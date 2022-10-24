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
}
