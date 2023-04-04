package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.data.model.DetailedCoin
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailedCoinUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository
) {

  operator fun invoke(
    id: String,
    vs_currency: String,
  ): Flow<Result<DetailedCoin>> {
    return coinsRepository.getDetailedCoinById(
      id = id,
      vs_currency = vs_currency,
    )
  }
}