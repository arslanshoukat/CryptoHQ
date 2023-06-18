package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.asResult
import com.haroof.data.model.Coin
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.UserSettingsRepository
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.model.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository,
  private val coinsRepository: CoinsRepository,
) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(sparkline: Boolean): Flow<Result<List<SimpleCoin>>> {
    return userSettingsRepository.defaultCurrency.flatMapLatest { defaultCurrency ->
      coinsRepository.getCoins(
        vs_currency = defaultCurrency.first,
        sparkline = sparkline
      ).asResult(
        mapper = Coin::toDomainModel,
        currencyUnit = defaultCurrency.second
      )
    }
  }
}