package com.haroof.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchListPreferencesDataSource @Inject constructor(
  private val dataStore: DataStore<Preferences>
) {

  val watchedCoinIds: Flow<List<String>> = dataStore.data.map {
    // TODO: remove hardcoded default values for watched coins
    (it[WATCHED_COIN_IDS] ?: setOf("bitcoin", "ethereum")).toList()
  }

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

  companion object {
    val WATCHED_COIN_IDS = stringSetPreferencesKey("watchedCoinIds")
  }
}