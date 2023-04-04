package com.haroof.data.repository

import com.haroof.common.model.Result
import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

  fun getCoins(): Flow<Result<List<Coin>>>

  fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<Result<List<Coin>>>

  fun getDetailedCoinById(
    id: String,
    vs_currency: String,
  ): Flow<Result<DetailedCoin>>
}