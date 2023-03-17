package com.haroof.data.repository

import android.util.Log
import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCoinsRepository @Inject constructor(
  //  todo: inject dispatchers here
  private val networkDataSource: NetworkDataSource
) : CoinsRepository {

  override suspend fun getCoins(): Result<List<Coin>> =
    withContext(Dispatchers.IO) {
      try {
        val coins = networkDataSource.getCoins(
          vs_currency = "usd",
          ids = "",
          sparkline = true
        ).map(CoinDto::toExternalModel)
        Result.Success(coins)
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        Result.Error(e)
      }
    }

  override suspend fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Result<List<Coin>> =
    withContext(Dispatchers.IO) {
      try {
        val coins = networkDataSource.getCoins(
          vs_currency = vs_currency,
          ids = ids.joinToString(","),
          sparkline = sparkline
        ).map(CoinDto::toExternalModel)
        Result.Success(coins)
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        Result.Error(e)
      }
    }

  companion object {
    const val TAG = "DefaultCoinsRepository"
  }
}