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
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun DataConversion.Configuration.localDateTime() {
    convert<LocalDateTime> {
        decode { values ->
            values.singleOrNull().let {
                try {
                    LocalDateTime.parse(it, dateTimeFormat)
                } catch (ex: DateTimeParseException) {
                    LocalDateTime.of(LocalDate.parse(it, dateFormat), LocalTime.MIN)
                }
            }
        }
        encode { value ->
            listOf(value.format(dateTimeFormat))
        }
    }
}

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(dateTimeFormat))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), dateTimeFormat)
    }
}

@Serializer(forClass = LocalTime::class)
object LocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)
    private val df: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.format(df))
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString(), df)
    }
}
