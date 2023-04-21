package com.haroof.domain

import com.haroof.data.model.Currency
import com.haroof.data.repository.CurrencyRepository
import com.haroof.domain.model.CurrencyUiModel
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
  private val currencyRepository: CurrencyRepository
) {

  operator fun invoke(): Flow<List<CurrencyUiModel>> {
    return currencyRepository.getCurrencies().mapLatest { currencies ->
      currencies.map(Currency::toExternalModel)
    }
  }
}