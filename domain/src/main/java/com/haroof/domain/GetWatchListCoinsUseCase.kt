package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.Result.Success
import com.haroof.common.model.asResult
import com.haroof.data.model.Coin
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.UserSettingsRepository
import com.haroof.data.repository.WatchListRepository
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.model.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetWatchListCoinsUseCase @Inject constructor(
  private val watchListRepository: WatchListRepository,
  private val userSettingsRepository: UserSettingsRepository,
  private val coinsRepository: CoinsRepository,
) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(): Flow<Result<List<SimpleCoin>>> {
    return combine(
      watchListRepository.watchedCoinIds,
      userSettingsRepository.defaultCurrency
    ) { coinIds, defaultCurrency ->
      coinIds to defaultCurrency
    }
      .flatMapLatest { (coinIds, defaultCurrency) ->
        if (coinIds.isEmpty()) flowOf(Success(emptyList()))
        else coinsRepository.getCoinsByIds(
          ids = coinIds,
          vs_currency = defaultCurrency,
          sparkline = false
        ).asResult(mapper = Coin::toDomainModel)
      }
  }
}