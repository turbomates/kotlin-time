package com.turbomates.time.period

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PeriodToDaysTest {
    @Test
    fun `hours rounds up to whole days`() {
        assertEquals(0, Period(0, PeriodType.HOURS).toDays())
        assertEquals(1, Period(1, PeriodType.HOURS).toDays())
        assertEquals(1, Period(23, PeriodType.HOURS).toDays())
        assertEquals(1, Period(24, PeriodType.HOURS).toDays())
        assertEquals(2, Period(25, PeriodType.HOURS).toDays())
        assertEquals(2, Period(48, PeriodType.HOURS).toDays())
        assertEquals(3, Period(49, PeriodType.HOURS).toDays())
    }

    @Test
    fun `days returns the same number`() {
        assertEquals(0, Period(0, PeriodType.DAYS).toDays())
        assertEquals(1, Period(1, PeriodType.DAYS).toDays())
        assertEquals(7, Period(7, PeriodType.DAYS).toDays())
    }

    @Test
    fun `weeks multiplies by 7`() {
        assertEquals(0, Period(0, PeriodType.WEEKS).toDays())
        assertEquals(7, Period(1, PeriodType.WEEKS).toDays())
        assertEquals(21, Period(3, PeriodType.WEEKS).toDays())
    }

    @Test
    fun `months multiplies by 30`() {
        assertEquals(0, Period(0, PeriodType.MONTHS).toDays())
        assertEquals(30, Period(1, PeriodType.MONTHS).toDays())
        assertEquals(90, Period(3, PeriodType.MONTHS).toDays())
    }

    @Test
    fun `years multiplies by 365`() {
        assertEquals(0, Period(0, PeriodType.YEARS).toDays())
        assertEquals(365, Period(1, PeriodType.YEARS).toDays())
        assertEquals(730, Period(2, PeriodType.YEARS).toDays())
    }
}
