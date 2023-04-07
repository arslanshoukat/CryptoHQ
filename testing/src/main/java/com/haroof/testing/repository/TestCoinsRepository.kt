package com.haroof.testing.repository

import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestCoinsRepository : CoinsRepository {

  private val coinsFlow =
    MutableSharedFlow<List<Coin>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  private val detailedCoinFlow =
    MutableSharedFlow<DetailedCoin>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  override fun getCoins(vs_currency: String): Flow<List<Coin>> {
    return coinsFlow
  }

  override fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<List<Coin>> {
    return coinsFlow.map { coins -> coins.filter { ids.contains(it.id) } }
  }

  override fun getDetailedCoinById(id: String, vs_currency: String): Flow<DetailedCoin> {
    return detailedCoinFlow
  }

  fun sendCoins(coins: List<Coin>): Boolean {
    return coinsFlow.tryEmit(coins)
  }

  fun sendDetailedCoin(detailedCoin: DetailedCoin): Boolean {
    return detailedCoinFlow.tryEmit(detailedCoin)
  }
}