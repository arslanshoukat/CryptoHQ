package com.haroof.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CryptoHqPreferencesDataSource @Inject constructor(
  private val dataStore: DataStore<Preferences>
) {

  val watchedCoinIds: Flow<List<String>> = dataStore.data.map {
    (it[WATCHED_COIN_IDS] ?: emptyList()).toList()
  }.distinctUntilChanged()

  val sourceCurrency: Flow<String> = dataStore.data.map {
    it[SOURCE_CURRENCY] ?: "btc"  //  default is Bitcoin
  }.distinctUntilChanged()

  val toCurrency: Flow<String> = dataStore.data.map {
    it[TO_CURRENCY] ?: "usd"    //  default is US Dollar
  }.distinctUntilChanged()

  suspend fun addToWatchList(coinId: String) {
    dataStore.edit {
      it[WATCHED_COIN_IDS] = it[WATCHED_COIN_IDS].orEmpty().plus(coinId)
    }
  }

  suspend fun removeFromWatchList(coinId: String) {
    dataStore.edit {
      it[WATCHED_COIN_IDS] = it[WATCHED_COIN_IDS].orEmpty().minus(coinId)
    }
  }

  suspend fun updateSourceCurrency(currencyCode: String) {
    dataStore.edit {
      it[SOURCE_CURRENCY] = currencyCode
    }
  }

  suspend fun updateToCurrency(currencyCode: String) {
    dataStore.edit {
      it[TO_CURRENCY] = currencyCode
    }
  }

  companion object {
    val WATCHED_COIN_IDS = stringSetPreferencesKey("watchedCoinIds")
    val SOURCE_CURRENCY = stringPreferencesKey("sourceCurrency")
    val TO_CURRENCY = stringPreferencesKey("toCurrency")
  }
}