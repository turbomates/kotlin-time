package com.turbomates.time.exposed

import com.turbomates.time.DateTimeRange
import org.jetbrains.exposed.sql.ExpressionWithColumnType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.QueryParameter
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun Query.andDateRange(range: DateTimeRange, expression: ExpressionWithColumnType<*>): Query {
    return apply {
        range.from?.let { andWhere { expression greaterEq it.queryValue(expression) } }
        range.to?.let {
            andWhere {
                expression lessEq OffsetDateTime.of(it.toLocalDateTime(), ZoneOffset.MAX).queryValue(expression)
            }
        }
    }
}

fun OffsetDateTime.queryValue(expression: ExpressionWithColumnType<*>): QueryParameter<*> {
    return if (expression.columnType is JavaLocalDateColumnType) {
        QueryParameter<LocalDate>(toLocalDate(), expression.columnType)
    } else QueryParameter(this, expression.columnType)
}
