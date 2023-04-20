package com.haroof.data.repository

import com.haroof.data.model.Currency
import com.haroof.data.model.toCurrency
import com.haroof.data.model.toCurrencyEntity
import com.haroof.database.dao.CurrencyDao
import com.haroof.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
  private val networkDataSource: NetworkDataSource,
  private val currencyDao: CurrencyDao,
) : CurrencyRepository {

  override fun getCurrencies(): Flow<List<Currency>> = flow {
    val exchangeRates = networkDataSource.getExchangeRates()
    val currencyEntities = exchangeRates.rates.map { (code, exchangeRate) ->
      exchangeRate.toCurrencyEntity(code)
    }

    currencyDao.deleteCurrencies()
    currencyDao.upsert(currencyEntities)

    // TODO: return reactive flow from database
    emit(currencyDao.getCurrencies().map { it.toCurrency() })
  }.flowOn(Dispatchers.IO)
}