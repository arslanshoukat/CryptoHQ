package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.WatchListRepository
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWatchListCoinsUseCase @Inject constructor(
  private val watchListRepository: WatchListRepository,
  private val coinsRepository: CoinsRepository
) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(): Flow<Result<List<SimpleCoin>>> {
    return watchListRepository.watchedCoinIds
      .flatMapLatest { coinIds ->
        if (coinIds.isEmpty()) flowOf(Success(emptyList()))
        else coinsRepository.getCoinsByIds(
          ids = coinIds,
          vs_currency = "usd",
          sparkline = false
        ).map { result ->
          when (result) {
            Loading -> Loading
            is Error -> Error(result.exception)
            is Success -> Success(result.data.map { it.toExternalModel() })
          }
        }
      }
  }
}