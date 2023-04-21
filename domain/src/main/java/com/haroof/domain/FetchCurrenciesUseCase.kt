package com.haroof.domain

import com.haroof.data.repository.CurrencyRepository
import javax.inject.Inject

class FetchCurrenciesUseCase @Inject constructor(
  private val currencyRepository: CurrencyRepository
) {

  suspend operator fun invoke() {
    return currencyRepository.syncCurrencies()
  }
}