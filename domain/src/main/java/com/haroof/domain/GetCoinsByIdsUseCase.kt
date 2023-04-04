package com.haroof.domain

import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsByIdsUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository
) {

  operator fun invoke(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<Result<List<Coin>>> {
    return coinsRepository.getCoinsByIds(
      vs_currency = vs_currency,
      ids = ids,
      sparkline = sparkline,
    )
  }
}