package com.haroof.domain.model

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class ChartEntry(
  val dateTime: ZonedDateTime,
  val value: Double,
) {
  constructor(values: List<Double>) : this(
    dateTime = ZonedDateTime.ofInstant(
      Instant.ofEpochMilli(values.first().toLong()),
      ZoneId.systemDefault()
    ),
    value = values[1]
  )
}

fun ChartEntry.toDataModel(): List<Double> =
  listOf(dateTime.toInstant().toEpochMilli().toDouble(), value)

