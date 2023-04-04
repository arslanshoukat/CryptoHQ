package com.haroof.data.repository

import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

  val watchedCoinIds: Flow<List<String>>

  suspend fun addToWatchList(coinId: String)

  suspend fun removeFromWatchList(coinId: String)

  fun isCoinWatched(coinId: String): Flow<Boolean>
}