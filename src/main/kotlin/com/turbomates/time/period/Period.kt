package com.turbomates.time.period

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.OffsetDateTime

@Serializable(with = PeriodSerializer::class)
data class Period(val number: Int, val periodType: PeriodType) {
    fun toDateRange(periodEnd: LocalDate = LocalDate.now()): Range<LocalDate> =
        Range(
            from = periodEnd.minusPeriod(this),
            to = periodEnd
        )

    fun toDateTimeRange(periodEnd: OffsetDateTime, includeCurrentPeriod: Boolean): Range<OffsetDateTime> {
        return if (includeCurrentPeriod) {
            Range(
                from = periodEnd.startOf(periodType).minusPeriod(this),
                to = periodEnd
            )
        } else {
            Range(
                from = periodEnd.startOf(periodType).minusPeriod(this),
                to = periodEnd.startOf(periodType)
            )
        }
    }

    fun toDateTimeRange(periodEnd: OffsetDateTime): Range<OffsetDateTime> {
        return Range(periodEnd.minusPeriod(this), periodEnd)
    }

    override fun toString(): String {
        return "$number ${periodType.symbol}"
    }

    companion object {
        fun fromString(periodString: String): Period {
            try {
                val (number, type) = Regex("([0-9]+?)([a-z])")
                    .find(periodString.replace(" ", ""))!!
                    .destructured
                return Period(
                    number.toInt(),
                    type.toPeriodType()
                )
            } catch (_: NullPointerException) {
                throw PeriodException(
                    "You should use format [number periodType] for example (7 d or 7d)." +
                        "Whitespaces doesn't matter."
                )
            }
        }

        private fun String.toPeriodType(): PeriodType {
            return PeriodType.values()
                .singleOrNull { periodType -> periodType.symbol == this }
                ?: throw PeriodException(
                    "Incorrect period type: $this. " +
                        "Period type can be only: ${PeriodType.values().map { it.symbol }}. "
                )
        }
    }
}

data class Range<T>(val from: T? = null, val to: T? = null)

@Serializable
enum class PeriodType(val symbol: String) {
    HOURS("h"),
    DAYS("d"),
    WEEKS("w"),
    MONTHS("m"),
    YEARS("y")
}

class PeriodException(message: String) : Exception(message)

object PeriodSerializer : KSerializer<Period> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Period")

    override fun deserialize(decoder: Decoder): Period {
        return Period.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Period) {
        encoder.encodeString(value.toString())
    }
}
