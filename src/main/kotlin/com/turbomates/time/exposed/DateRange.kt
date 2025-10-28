package com.turbomates.time.exposed

import com.turbomates.time.DateTimeRange
import java.time.LocalDate
import java.time.OffsetDateTime
import org.jetbrains.exposed.v1.core.ExpressionWithColumnType
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.core.QueryParameter
import org.jetbrains.exposed.v1.core.greaterEq
import org.jetbrains.exposed.v1.core.lessEq
import org.jetbrains.exposed.v1.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.v1.jdbc.andWhere

fun Query.andDateRange(range: DateTimeRange, expression: ExpressionWithColumnType<*>): Query {
    return apply {
        range.from?.let { andWhere { expression greaterEq it.queryValue(expression) } }
        range.to?.let { andWhere { expression lessEq it.queryValue(expression) } }
    }
}

fun OffsetDateTime.queryValue(expression: ExpressionWithColumnType<*>): QueryParameter<*> {
    return if (expression.columnType is JavaLocalDateColumnType) {
        QueryParameter<LocalDate>(toLocalDate(), expression.columnType as JavaLocalDateColumnType)
    } else {
        QueryParameter(this, expression.columnType as UTCDateTimeColumn)
    }
}
