package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.data.model.DetailedCoin
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.WatchListRepository
import com.haroof.domain.model.WatchableDetailedCoin
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetWatchableDetailedCoinUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository,
  private val watchListRepository: WatchListRepository,
) {

  operator fun invoke(
    id: String,
    vs_currency: String,
  ): Flow<Result<WatchableDetailedCoin>> {
    return combine<DetailedCoin, Boolean, Result<WatchableDetailedCoin>>(
      coinsRepository.getDetailedCoinById(
        id = id,
        vs_currency = vs_currency,
      ),
      watchListRepository.isCoinWatched(id)
    ) { detailedCoin, isWatched ->
      Result.Success(detailedCoin.toExternalModel(isWatched))
    }
      .onStart { emit(Result.Loading) }
      .catch {
        if (it is CancellationException) throw it  //  todo: consider if we need to return a result here
        else emit(Result.Error(it))
      }
      .flowOn(Dispatchers.IO)
  }
}