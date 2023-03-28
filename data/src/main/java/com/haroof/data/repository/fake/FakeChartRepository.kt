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
        prices = emptyList(),
        totalVolumes = emptyList(),
      )
    )
  }
}