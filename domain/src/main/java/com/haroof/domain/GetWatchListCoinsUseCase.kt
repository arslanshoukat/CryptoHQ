package com.haroof.domain

import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.WatchListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWatchListCoinsUseCase @Inject constructor(
  private val watchListRepository: WatchListRepository,
  private val coinsRepository: CoinsRepository
) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(): Flow<Result<List<Coin>>> {
    return watchListRepository.watchedCoinIds
      .flatMapLatest { coinIds ->
        flow {
          emit(
            if (coinIds.isEmpty()) Result.Success(emptyList())
            else coinsRepository.getCoinsByIds(
              ids = coinIds,
              vs_currency = "usd",
              sparkline = false
            )
          )
        }
      }
  }
}