package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.WatchListRepository
import com.haroof.domain.model.WatchableDetailedCoin
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWatchableDetailedCoinUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository,
  private val watchListRepository: WatchListRepository,
) {

  operator fun invoke(
    id: String,
    vs_currency: String,
  ): Flow<Result<WatchableDetailedCoin>> {
    return combine(
      coinsRepository.getDetailedCoinById(
        id = id,
        vs_currency = vs_currency,
      ),
      watchListRepository.isCoinWatched(id)
    ) { result, isWatched ->
      when (result) {
        Loading -> Loading
        is Error -> Error(result.exception)
        is Success -> Success(result.data.toExternalModel(isWatched))
      }
    }
  }
}