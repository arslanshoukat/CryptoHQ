package com.haroof.testing.repository

import com.haroof.data.model.Currency
import com.haroof.data.repository.CurrencyRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestCurrencyRepository : CurrencyRepository {

  private val currenciesFlow =
    MutableSharedFlow<List<Currency>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  override fun getCurrencies(): Flow<List<Currency>> {
    return currenciesFlow
  }

  fun sendCurrencies(currencies: List<Currency>) {
    currenciesFlow.tryEmit(currencies)
  }
}