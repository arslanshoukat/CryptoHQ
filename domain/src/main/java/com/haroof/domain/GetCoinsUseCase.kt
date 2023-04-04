package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.data.model.Coin
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository
) {

  operator fun invoke(): Flow<Result<List<Coin>>> {
    return coinsRepository.getCoins()
  }
}