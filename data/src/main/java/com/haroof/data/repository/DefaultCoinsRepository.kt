package com.haroof.data.repository

import android.util.Log
import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.Result
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCoinsRepository @Inject constructor(
  private val networkDataSource: NetworkDataSource
) : CoinsRepository {

  override fun getCoins(): Flow<Result<List<Coin>>> = flow {
    withContext(Dispatchers.IO) {
      emit(Result.Loading)
      try {
        val coins = networkDataSource.getCoins(
          vs_currency = "usd",
          ids = emptyList(),
          sparkline = true
        ).map(CoinDto::toExternalModel)
        emit(Result.Success(coins))
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        emit(Result.Error(e))
      }
    }
  }

  override fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<Result<List<Coin>>> = flow {
    withContext(Dispatchers.IO) {
      emit(Result.Loading)
      try {
        val coins = networkDataSource.getCoins(
          vs_currency = vs_currency,
          ids = ids,
          sparkline = sparkline
        ).map(CoinDto::toExternalModel)
        emit(Result.Success(coins))
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        emit(Result.Error(e))
      }
    }
  }

  override fun getDetailedCoinById(
    id: String,
    vs_currency: String,
  ): Flow<Result<DetailedCoin>> = flow {
    withContext(Dispatchers.IO) {
      emit(Result.Loading)
      try {
        val coins = networkDataSource.getDetailedCoin(id).toExternalModel(vs_currency)
        emit(Result.Success(coins))
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        emit(Result.Error(e))
      }
    }
  }

  companion object {
    const val TAG = "DefaultCoinsRepository"
  }
}