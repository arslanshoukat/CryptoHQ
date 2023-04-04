package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.data.repository.ChartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChartDataUseCase @Inject constructor(
  private val chartRepository: ChartRepository
) {

  operator fun invoke(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<Result<List<Double>>> {
    return chartRepository.getChartData(
      id = id,
      vs_currency = vs_currency,
      days = days,
      interval = interval,
    ).map { result ->
      when (result) {
        Loading -> Loading
        is Error -> Error(result.exception)
        is Success -> Success(result.data.prices.map { it[1] }.dropLast(1))
      }
    }
  }
}