package com.turbomates.time

import io.ktor.util.converters.DataConversion
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

val nowUTC: OffsetDateTime
    get() = OffsetDateTime.now(ZoneOffset.UTC)

object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(dateTimeFormat))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString(), dateTimeFormat)
    }
}

fun DataConversion.Configuration.offsetDateTime() {
    convert<OffsetDateTime> {
        decode { values ->
            values.single().let {
                try {
                    OffsetDateTime.parse(it, dateTimeFormat)
                } catch (ex: DateTimeParseException) {
                    OffsetDateTime.of(LocalDate.parse(it, dateFormat), LocalTime.MIN, ZoneOffset.UTC)
                }
            }
        }
        encode { value ->
            listOf(value.format(dateTimeFormat))
        }
    }
}

fun LocalDateTime.toOffsetDateTime(): OffsetDateTime {
    return OffsetDateTime.of(this, ZoneOffset.UTC)
}
