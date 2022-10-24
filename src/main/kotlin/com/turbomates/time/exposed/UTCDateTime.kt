package com.turbomates.time.exposed

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Function
import org.jetbrains.exposed.sql.IDateColumnType
import org.jetbrains.exposed.sql.QueryBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun Table.datetime(name: String): Column<OffsetDateTime> = registerColumn(name, UTCDateTimeColumn.INSTANCE)

class UTCNow : Function<OffsetDateTime>(UTCDateTimeColumn.INSTANCE) {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        +"CURRENT_TIMESTAMP"
    }
}

@Suppress("MagicNumber")
class UTCDateTimeColumn : ColumnType(), IDateColumnType {
    override val hasTimePart: Boolean = true
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC)

    override fun sqlType(): String = currentDialect.dataTypeProvider.dateTimeType()

    override fun nonNullValueToString(value: Any): String {
        val instant = when (value) {
            is String -> return "'$value'"
            is OffsetDateTime -> value
            is Instant -> value
            is java.sql.Date -> Instant.ofEpochMilli(value.time)
            is java.sql.Timestamp -> Instant.ofEpochSecond(value.time / 1000, value.nanos.toLong())
            else -> error("Unexpected value: $value of ${value::class.qualifiedName.orEmpty()}")
        }

        return "'${formatter.format(instant)}'"
    }

    override fun valueFromDB(value: Any): Any = when (value) {
        is OffsetDateTime -> value
        is java.sql.Date -> longToDateTime(value.time)
        is java.sql.Timestamp -> longToDateTime(value.time / 1000, value.nanos.toLong())
        is Int -> longToDateTime(value.toLong())
        is Long -> longToDateTime(value)
        else -> super.valueFromDB(value)
    }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is Instant -> java.sql.Timestamp.from(value)
        is OffsetDateTime -> {
            val instant = value.toInstant()
            java.sql.Timestamp.from(instant)
        }
        else -> value
    }

    private fun longToDateTime(millis: Long) = OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC)
    private fun longToDateTime(seconds: Long, nanos: Long) =
        OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanos), ZoneOffset.UTC)

    companion object {
        internal val INSTANCE = UTCDateTimeColumn()
    }
}
