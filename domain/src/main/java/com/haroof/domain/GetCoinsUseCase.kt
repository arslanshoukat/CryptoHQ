package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.asResult
import com.haroof.data.model.Coin
import com.haroof.data.repository.CoinsRepository
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository
) {

  operator fun invoke(): Flow<Result<List<SimpleCoin>>> {
    return coinsRepository.getCoins(vs_currency = "usd").asResult(mapper = Coin::toExternalModel)
  }
}