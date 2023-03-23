package com.haroof.data.repository

import com.haroof.data.model.Coin
import com.haroof.data.model.Result

interface CoinsRepository {

  suspend fun getCoins(): Result<List<Coin>>

  suspend fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Result<List<Coin>>

  suspend fun getCoinById(
    id: String,
    vs_currency: String,
    sparkline: Boolean
  ): Result<Coin?>
}