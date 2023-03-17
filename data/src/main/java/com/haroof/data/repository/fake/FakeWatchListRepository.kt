package com.haroof.data.repository.fake

import com.haroof.data.FakeData
import com.haroof.data.repository.WatchListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeWatchListRepository : WatchListRepository {

  override val watchedCoinIds: Flow<List<String>> = flowOf(FakeData.WATCH_LIST_COIN_IDS)

  override suspend fun addToWatchList(coinId: String) {
    TODO("Not yet implemented")
  }

  override suspend fun removeFromWatchList(coinId: String) {
    TODO("Not yet implemented")
  }
}