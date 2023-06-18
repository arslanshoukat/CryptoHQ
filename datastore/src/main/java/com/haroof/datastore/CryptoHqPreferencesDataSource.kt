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

  val targetCurrency: Flow<String> = dataStore.data.map {
    it[TARGET_CURRENCY] ?: "usd"    //  default is US Dollar
  }.distinctUntilChanged()

  val defaultCurrency: Flow<Pair<String, String>> = dataStore.data.map {
    //  default currency code = US Dollar, unit = $
    Pair(it[DEFAULT_CURRENCY_CODE] ?: "usd", it[DEFAULT_CURRENCY_UNIT] ?: "$")
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

  suspend fun updateTargetCurrency(currencyCode: String) {
    dataStore.edit {
      it[TARGET_CURRENCY] = currencyCode
    }
  }

  suspend fun updateDefaultCurrency(currencyCode: String, currencyUnit: String) {
    dataStore.edit {
      it[DEFAULT_CURRENCY_CODE] = currencyCode
      it[DEFAULT_CURRENCY_UNIT] = currencyUnit
    }
  }

  companion object {
    val WATCHED_COIN_IDS = stringSetPreferencesKey("watchedCoinIds")
    val SOURCE_CURRENCY = stringPreferencesKey("sourceCurrency")
    val TARGET_CURRENCY = stringPreferencesKey("targetCurrency")
    val DEFAULT_CURRENCY_CODE = stringPreferencesKey("defaultCurrencyCode")
    val DEFAULT_CURRENCY_UNIT = stringPreferencesKey("defaultCurrencyUnit")
  }
}