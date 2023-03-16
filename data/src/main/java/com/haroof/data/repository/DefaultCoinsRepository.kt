package com.haroof.data.repository

import android.util.Log
import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.model.asResult
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
          sparkline = true
        ).map(CoinDto::toExternalModel)
        Result.Success(coins)
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        Result.Error(e)
      }
    }

  private val flow = MutableStateFlow<List<Coin>>(FakeData.COINS)

  override fun getWatchListCoinsFlow(): Flow<Result<List<Coin>>> = flow.asResult()

  companion object {
    const val TAG = "DefaultCoinsRepository"
  }
}