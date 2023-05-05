package com.haroof.data.repository

import android.util.Log
import com.haroof.data.model.Currency
import com.haroof.data.model.toCurrency
import com.haroof.data.model.toCurrencyEntity
import com.haroof.database.dao.CurrencyDao
import com.haroof.network.NetworkDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
  private val networkDataSource: NetworkDataSource,
  private val currencyDao: CurrencyDao,
) : CurrencyRepository {

  override suspend fun syncCurrencies() {
    withContext(Dispatchers.IO) {
      Log.d(TAG, "--- SYNCING CURRENCIES WITH NETWORK ---")
      try {
        val exchangeRates = networkDataSource.getExchangeRates()
        val currencyEntities = exchangeRates.rates.map { (code, exchangeRate) ->
          exchangeRate.toCurrencyEntity(code)
        }

        currencyDao.deleteCurrencies()
        currencyDao.upsert(currencyEntities)
      } catch (cancellationException: CancellationException) {
        throw cancellationException
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
      }
    }
  }

  override fun getCurrencies(): Flow<List<Currency>> {
    return currencyDao.getCurrencies().mapLatest { currencyEntities ->
      currencyEntities.map { it.toCurrency() }
    }
  }

  companion object {
    private const val TAG = "DefaultCurrencyRepo"
  }
}