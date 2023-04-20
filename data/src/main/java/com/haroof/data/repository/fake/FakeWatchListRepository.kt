package com.haroof.data.repository.fake

import com.haroof.data.repository.WatchListRepository
import com.haroof.datastore.CryptoHqPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeWatchListRepository @Inject constructor(
  private val preferencesDataSource: CryptoHqPreferencesDataSource
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