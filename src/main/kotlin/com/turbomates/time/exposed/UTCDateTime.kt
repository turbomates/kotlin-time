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

class CurrentTimestamp : Function<OffsetDateTime>(UTCDateTimeColumn.INSTANCE) {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        +"CURRENT_TIMESTAMP"
    }
}

@Suppress("MagicNumber")
class UTCDateTimeColumn : ColumnType<OffsetDateTime>(), IDateColumnType {
    override val hasTimePart: Boolean = true
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC)

    override fun sqlType(): String = currentDialect.dataTypeProvider.dateTimeType()

    override fun nonNullValueToString(value: OffsetDateTime): String {
        return "'${formatter.format(value)}'"
    }

    override fun valueFromDB(value: Any): OffsetDateTime? = when (value) {
        is OffsetDateTime -> value
        is java.sql.Date -> longToDateTime(value.time)
        is java.sql.Timestamp -> longToDateTime(value.time / 1000, value.nanos.toLong())
        is Int -> longToDateTime(value.toLong())
        is Long -> longToDateTime(value)
        else -> throw IllegalArgumentException("Unexpected value: $value of ${value::class.qualifiedName.orEmpty()}")
    }

    override fun notNullValueToDB(value: OffsetDateTime): Any {
        val instant = value.toInstant()
        return java.sql.Timestamp.from(instant)
    }

    private fun longToDateTime(millis: Long) = OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC)
    private fun longToDateTime(seconds: Long, nanos: Long) =
        OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanos), ZoneOffset.UTC)

    companion object {
        internal val INSTANCE = UTCDateTimeColumn()
    }
}
