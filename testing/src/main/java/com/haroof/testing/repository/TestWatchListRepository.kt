package com.haroof.testing.repository

import com.haroof.data.repository.WatchListRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TestWatchListRepository @Inject constructor() : WatchListRepository {

  private val _watchedCoinIds =
    MutableSharedFlow<List<String>>(replay = 1, onBufferOverflow = DROP_OLDEST)

  private val currentWatchedCoinIds get() = _watchedCoinIds.replayCache.firstOrNull() ?: emptyList()

  override val watchedCoinIds: Flow<List<String>> = _watchedCoinIds.filterNotNull()

  override suspend fun addToWatchList(coinId: String) {
    _watchedCoinIds.tryEmit(currentWatchedCoinIds + coinId)
  }

  override suspend fun removeFromWatchList(coinId: String) {
    _watchedCoinIds.tryEmit(currentWatchedCoinIds - coinId)
  }

  override fun isCoinWatched(coinId: String): Flow<Boolean> {
    return watchedCoinIds.map { it.contains(coinId) }.distinctUntilChanged()
  }

  fun sendWatchedCoinsIds(watchedCoinIds: List<String>) {
    _watchedCoinIds.tryEmit(watchedCoinIds)
  }
}