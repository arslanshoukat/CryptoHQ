package com.haroof.testing.data

import com.haroof.domain.model.ChartEntry
import java.time.ZoneId
import java.time.ZonedDateTime

object ChartEntryTestData {

  val LIST = listOf(
    ChartEntry(
      ZonedDateTime.of(2023, 12, 10, 12, 12, 12, 0, ZoneId.systemDefault()),
      25000.0
    ),
    ChartEntry(
      ZonedDateTime.of(2023, 12, 11, 12, 12, 12, 0, ZoneId.systemDefault()),
      25001.0
    ),
    ChartEntry(
      ZonedDateTime.of(2023, 12, 12, 12, 12, 12, 0, ZoneId.systemDefault()),
      25002.0
    ),
    ChartEntry(
      ZonedDateTime.of(2023, 12, 13, 12, 12, 12, 0, ZoneId.systemDefault()),
      25003.0
    ),
    ChartEntry(
      ZonedDateTime.of(2023, 12, 14, 12, 12, 12, 0, ZoneId.systemDefault()),
      25004.0
    ),
    ChartEntry(
      ZonedDateTime.of(2023, 12, 15, 12, 12, 12, 0, ZoneId.systemDefault()),
      25005.0
    ),
    ChartEntry(
      ZonedDateTime.of(2023, 12, 16, 12, 12, 12, 0, ZoneId.systemDefault()),
      25006.0
    ),
  )
}