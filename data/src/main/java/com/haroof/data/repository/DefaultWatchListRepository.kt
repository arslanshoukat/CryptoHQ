package com.haroof.data.repository

import com.haroof.datastore.WatchListPreferencesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultWatchListRepository @Inject constructor(
  private val preferencesDataSource: WatchListPreferencesDataSource
) : WatchListRepository {

  override val watchedCoinIds: Flow<List<String>>
    get() = preferencesDataSource.watchedCoinIds

  override suspend fun addToWatchList(coinId: String) {
    withContext(Dispatchers.IO) {
      preferencesDataSource.addToWatchList(coinId)
    }
  }

  override suspend fun removeFromWatchList(coinId: String) {
    withContext(Dispatchers.IO) {
      preferencesDataSource.removeFromWatchList(coinId)
    }
  }
}