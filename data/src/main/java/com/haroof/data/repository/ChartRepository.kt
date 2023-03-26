package com.haroof.data.repository

import com.haroof.data.model.ChartData
import com.haroof.data.model.Result

interface ChartRepository {

  suspend fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Result<ChartData>
}