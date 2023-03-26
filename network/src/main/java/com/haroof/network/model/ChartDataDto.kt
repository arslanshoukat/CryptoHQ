package com.haroof.network.model

import kotlinx.serialization.Serializable

/**
 * Data class to represent chart data.
 * Values are formatted as List<List<Double>> There will be n values depending on
 * interval selected. Nested list contains list of doubles formatted as  [timestamp, value].
 */
@Serializable
data class ChartDataDto(
  val market_caps: List<List<Double>>?,
  val prices: List<List<Double>>?,
  val total_volumes: List<List<Double>>?,
)