package com.haroof.data.repository.fake

import com.haroof.data.model.Currency
import com.haroof.data.model.toCurrency
import com.haroof.data.model.toCurrencyEntity
import com.haroof.data.repository.CurrencyRepository
import com.haroof.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeCurrencyRepository @Inject constructor(
  private val networkDataSource: FakeNetworkDataSource,
) : CurrencyRepository {

  override suspend fun syncCurrencies() {}

  override fun getCurrencies(): Flow<List<Currency>> = flow {
    val exchangeRates = networkDataSource.getExchangeRates()
    val currencies = exchangeRates.rates.map { (code, exchangeRate) ->
      exchangeRate.toCurrencyEntity(code).toCurrency()
    }

    emit(currencies)
  }.flowOn(Dispatchers.IO)
}