package com.haroof.data.repository.fake

import com.haroof.data.model.ChartData
import com.haroof.data.model.toExternalModel
import com.haroof.data.repository.ChartRepository
import com.haroof.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeChartRepository @Inject constructor(
  private val networkDataSource: FakeNetworkDataSource
) : ChartRepository {

  override fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<ChartData> = flow {
    val chartData = networkDataSource.getChartData(
      id = id,
      vs_currency = vs_currency,
      days = days,
      interval = interval,
    ).toExternalModel()
    emit(chartData)
  }.flowOn(Dispatchers.IO)
}