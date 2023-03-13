package com.haroof.data.repository

import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

  suspend fun getCoins(): Result<List<Coin>>

  fun getWatchListCoinsFlow(): Flow<Result<List<Coin>>>
}