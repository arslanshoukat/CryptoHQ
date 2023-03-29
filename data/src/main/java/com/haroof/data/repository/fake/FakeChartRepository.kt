package com.haroof.data.repository.fake

import com.haroof.data.model.ChartData
import com.haroof.data.model.Result
import com.haroof.data.repository.ChartRepository
import javax.inject.Inject

class FakeChartRepository @Inject constructor() : ChartRepository {
  override suspend fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Result<ChartData> {
    return Result.Success(
      ChartData(
        marketCaps = emptyList(),
        prices = listOf(
          listOf(1678406400000.0, 20376.320007428807),
          listOf(1678492800000.0, 20195.2289502733),
          listOf(1678579200000.0, 20521.55615175117),
          listOf(1678665600000.0, 22095.71339833569),
          listOf(1678752000000.0, 24178.95532797469),
          listOf(1678838400000.0, 24758.765085827567),
          listOf(1678924800000.0, 24470.88205445031),
          listOf(1678989793000.0, 24815.327628227824)
        ),
        totalVolumes = emptyList(),
      )
    )
  }
}