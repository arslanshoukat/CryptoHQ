package com.haroof.data.repository

import com.haroof.common.model.Result
import com.haroof.common.model.resultFlow
import com.haroof.data.model.ChartData
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.ChartDataDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultChartRepository @Inject constructor(
  private val networkDataSource: NetworkDataSource
) : ChartRepository {

  override fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<Result<ChartData>> = resultFlow<ChartData, ChartDataDto>(
    mapper = ChartDataDto::toExternalModel,
    block = {
      networkDataSource.getChartData(
        id = id,
        vs_currency = vs_currency,
        days = days,
        interval = interval,
      )
    }
  )
}