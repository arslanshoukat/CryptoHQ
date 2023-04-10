package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.data.model.ChartData
import com.haroof.data.repository.ChartRepository
import com.haroof.domain.model.ChartEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetChartDataUseCase @Inject constructor(
  private val chartRepository: ChartRepository
) {

  operator fun invoke(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<Result<List<ChartEntry>>> {
    return chartRepository.getChartData(
      id = id,
      vs_currency = vs_currency,
      days = days,
      interval = interval,
    )
      .map<ChartData, Result<List<ChartEntry>>> { chartData ->
        Result.Success(chartData.prices.map { ChartEntry(it) })
      }
      .onStart { emit(Result.Loading) }
      .catch {
        if (it is CancellationException) throw it  //  todo: consider if we need to return a result here
        else emit(Result.Error(it))
      }
      .flowOn(Dispatchers.IO)
  }
}