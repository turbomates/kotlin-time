package com.turbomates.time

import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.Date

fun Date.toOffsetDateTime(): OffsetDateTime = toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime()
