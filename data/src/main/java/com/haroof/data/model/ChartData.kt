package com.haroof.data.model

import com.haroof.network.model.ChartDataDto

/**
 * Data class to represent chart data.
 * Values are formatted as List<List<Double>> There will be n values depending on
 * interval selected. Nested list contains list of doubles formatted as  [timestamp, value].
 */
data class ChartData(
  val prices: List<List<Double>>,
)

fun ChartDataDto.toExternalModel() = ChartData(
  prices = prices.orEmpty(),
)