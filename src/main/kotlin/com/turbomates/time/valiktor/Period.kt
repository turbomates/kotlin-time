package com.turbomates.time.valiktor

import com.turbomates.time.period.Period
import com.turbomates.time.period.Range
import org.valiktor.Constraint
import org.valiktor.Validator

/**
 * Represents a constraint that validate period range
 */
object PeriodRange : Constraint {
    override val name: String = "The period range is not valid format"
}

fun <E> Validator<E>.Property<Range<Period>?>.isValidPeriodRange(): Validator<E>.Property<Range<Period>?> =
    this.validate(PeriodRange) { periods ->
        periods != null && when {
            periods.from == null && periods.to == null -> false
            periods.from != null && periods.to != null && periods.from.periodType != periods.to.periodType -> false
            else -> true
        }
    }
