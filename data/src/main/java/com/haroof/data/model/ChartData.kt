package com.haroof.data.model

import com.haroof.network.model.ChartDataDto

/**
 * Data class to represent chart data.
 * Values are formatted as List<List<Double>> There will be n values depending on
 * interval selected. Nested list contains list of doubles formatted as  [timestamp, value].
 */
data class ChartData(
  val market_caps: List<List<Double>>,
  val prices: List<List<Double>>,
  val total_volumes: List<List<Double>>,
)

fun ChartDataDto.toExternalModel() = ChartData(
  market_caps = market_caps.orEmpty(),
  prices = prices.orEmpty(),
  total_volumes = total_volumes.orEmpty(),
)