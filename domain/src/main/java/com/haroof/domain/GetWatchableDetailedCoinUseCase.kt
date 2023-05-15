package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.data.model.DetailedCoin
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.UserSettingsRepository
import com.haroof.data.repository.WatchListRepository
import com.haroof.domain.model.WatchableDetailedCoin
import com.haroof.domain.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetWatchableDetailedCoinUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository,
  private val coinsRepository: CoinsRepository,
  private val watchListRepository: WatchListRepository,
) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(id: String): Flow<Result<WatchableDetailedCoin>> {
    return userSettingsRepository.defaultCurrency
      .flatMapLatest { defaultCurrency ->
        combine<DetailedCoin, Boolean, Result<WatchableDetailedCoin>>(
          coinsRepository.getDetailedCoinById(
            id = id,
            vs_currency = defaultCurrency,
          ),
          watchListRepository.isCoinWatched(id)
        ) { detailedCoin, isWatched ->
          Result.Success(detailedCoin.toDomainModel(isWatched))
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