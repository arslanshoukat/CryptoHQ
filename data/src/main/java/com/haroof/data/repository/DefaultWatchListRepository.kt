package com.haroof.data.repository

import com.haroof.datastore.WatchListPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultWatchListRepository @Inject constructor(
  private val preferencesDataSource: WatchListPreferencesDataSource
) : WatchListRepository {

  override val watchedCoinIds: Flow<List<String>>
    get() = preferencesDataSource.watchedCoinIds.distinctUntilChanged()

  override suspend fun addToWatchList(coinId: String) {
    preferencesDataSource.addToWatchList(coinId)
  }

  override suspend fun removeFromWatchList(coinId: String) {
    preferencesDataSource.removeFromWatchList(coinId)
  }

  override fun isCoinWatched(coinId: String): Flow<Boolean> {
    return watchedCoinIds.map { it.contains(coinId) }.distinctUntilChanged()
  }
}