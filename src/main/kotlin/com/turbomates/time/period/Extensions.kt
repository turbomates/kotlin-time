package com.turbomates.time.period

import java.time.LocalDate
import java.time.OffsetDateTime
import kotlin.text.toLong

fun OffsetDateTime.startOf(periodType: PeriodType): OffsetDateTime {
    return when (periodType) {
        PeriodType.HOURS -> withMinute(0)
            .withSecond(0)
            .withNano(0)

        PeriodType.DAYS -> withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        PeriodType.WEEKS -> minusDays(dayOfWeek.value.toLong() - 1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        PeriodType.MONTHS -> withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        PeriodType.YEARS -> withDayOfYear(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
    }
}

fun OffsetDateTime.endOf(periodType: PeriodType): OffsetDateTime {
    return when (periodType) {
        PeriodType.HOURS -> withMinute(59)
            .withSecond(59)
            .withNano(999_999_999)

        PeriodType.DAYS -> withHour(23)
            .withMinute(59)
            .withSecond(59)
            .withNano(999_999_999)

        PeriodType.WEEKS -> plusDays(7 - dayOfWeek.value.toLong())
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
            .withNano(999_999_999)

        PeriodType.MONTHS -> withDayOfMonth(toLocalDate().lengthOfMonth())
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
            .withNano(999_999_999)

        PeriodType.YEARS -> withDayOfYear(toLocalDate().lengthOfYear())
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
            .withNano(999_999_999)
    }
}

fun OffsetDateTime.plusPeriod(period: Period): OffsetDateTime {
    return when (period.periodType) {
        PeriodType.HOURS -> plusHours(period.number.toLong())
        PeriodType.DAYS -> plusDays(period.number.toLong())
        PeriodType.WEEKS -> plusWeeks(period.number.toLong())
        PeriodType.MONTHS -> plusMonths(period.number.toLong())
        PeriodType.YEARS -> plusYears(period.number.toLong())
    }
}

fun OffsetDateTime.minusPeriod(period: Period): OffsetDateTime {
    return when (period.periodType) {
        PeriodType.HOURS -> minusHours(period.number.toLong())
        PeriodType.DAYS -> minusDays(period.number.toLong())
        PeriodType.WEEKS -> minusWeeks(period.number.toLong())
        PeriodType.MONTHS -> minusMonths(period.number.toLong())
        PeriodType.YEARS -> minusYears(period.number.toLong())
    }
}

fun LocalDate.startOf(periodType: PeriodType): LocalDate {
    return when (periodType) {
        PeriodType.WEEKS -> withDayOfMonth(dayOfMonth - dayOfWeek.value + 1)
        PeriodType.MONTHS -> withDayOfMonth(1)
        PeriodType.YEARS -> withDayOfYear(1)
        PeriodType.HOURS, PeriodType.DAYS -> throw IllegalArgumentException("Incorrect period type")
    }
}

fun LocalDate.minusPeriod(period: Period): LocalDate {
    return when (period.periodType) {
        PeriodType.DAYS -> minusDays(period.number.toLong())
        PeriodType.WEEKS -> minusWeeks(period.number.toLong())
        PeriodType.MONTHS -> minusMonths(period.number.toLong())
        PeriodType.YEARS -> minusYears(period.number.toLong())
        PeriodType.HOURS -> throw UnsupportedOperationException("Incorrect period type ${period.periodType} for LocalDate")
    }
}
