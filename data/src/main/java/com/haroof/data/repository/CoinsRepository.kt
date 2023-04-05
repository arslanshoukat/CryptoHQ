package com.haroof.data.repository

import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

  fun getCoins(vs_currency: String): Flow<List<Coin>>

  fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<List<Coin>>

  fun getDetailedCoinById(
    id: String,
    vs_currency: String,
  ): Flow<DetailedCoin>
}