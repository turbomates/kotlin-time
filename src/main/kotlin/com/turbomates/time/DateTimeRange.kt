@file:UseSerializers(OffsetDateTimeSerializer::class)
package com.turbomates.time

import java.time.OffsetDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class DateTimeRange(val from: OffsetDateTime? = null, val to: OffsetDateTime? = null)
