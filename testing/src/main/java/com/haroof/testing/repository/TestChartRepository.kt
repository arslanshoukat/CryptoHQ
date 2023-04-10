package com.haroof.testing.repository

import com.haroof.data.model.ChartData
import com.haroof.data.repository.ChartRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestChartRepository : ChartRepository {

  private val chartDataFlow =
    MutableSharedFlow<ChartData>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  override fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<ChartData> {
    return chartDataFlow
  }

  fun sendChartData(chartData: ChartData) {
    chartDataFlow.tryEmit(chartData)
  }
}