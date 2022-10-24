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
import java.time.OffsetDateTime
import java.time.format.DateTimeParseException

fun DataConversion.Configuration.localDate() {
    convert<LocalDate> {
        decode { values ->
            values.singleOrNull()?.let {
                try {
                    LocalDate.parse(it, dateFormat)
                } catch (ex: DateTimeParseException) {
                    OffsetDateTime.parse(it, dateTimeFormat).toLocalDate()
                }
            }
            LocalDate.parse(values.singleOrNull())
        }
        encode { value ->
            listOf(value.format(dateFormat))
        }
    }
}

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(dateFormat))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}
