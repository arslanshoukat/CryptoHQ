package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.data.model.ChartData
import com.haroof.data.repository.ChartRepository
import com.haroof.data.repository.UserSettingsRepository
import com.haroof.domain.model.ChartEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetChartDataUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository,
  private val chartRepository: ChartRepository,
) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(
    id: String,
    days: String,
    interval: String
  ): Flow<Result<List<ChartEntry>>> {
    return userSettingsRepository.defaultCurrency.flatMapLatest { defaultCurrency ->
      chartRepository.getChartData(
        id = id,
        vs_currency = defaultCurrency.first,
        days = days,
        interval = interval,
      )
        .map<ChartData, Result<List<ChartEntry>>> { chartData ->
          Result.Success(chartData.prices.map { ChartEntry(it) })
        }
        .onStart { emit(Result.Loading) }
        .catch {
          if (it is CancellationException) throw it
          else emit(Result.Error(it))
        }
        .flowOn(Dispatchers.IO)
    }
  }
}