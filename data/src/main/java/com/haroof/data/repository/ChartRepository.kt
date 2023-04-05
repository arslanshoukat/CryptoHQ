package com.haroof.data.repository

import com.haroof.data.model.ChartData
import kotlinx.coroutines.flow.Flow

interface ChartRepository {

  fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<ChartData>
}