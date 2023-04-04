package com.haroof.data.repository

import com.haroof.common.model.Result
import com.haroof.common.model.listResultFlow
import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DefaultCoinsRepository @Inject constructor(
  private val networkDataSource: NetworkDataSource
) : CoinsRepository {

  override fun getCoins(): Flow<Result<List<Coin>>> = listResultFlow(
    mapper = CoinDto::toExternalModel,
    block = {
      networkDataSource.getCoins(
        vs_currency = "usd",
        ids = emptyList(),
        sparkline = true
      )
    }
  )

  override fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<Result<List<Coin>>> = listResultFlow(
    mapper = CoinDto::toExternalModel,
    block = {
      networkDataSource.getCoins(
        vs_currency = vs_currency,
        ids = ids,
        sparkline = sparkline
      )
    }
  )

  override fun getDetailedCoinById(
    id: String,
    vs_currency: String,
  ): Flow<Result<DetailedCoin>> {
    return flow {
      val response = networkDataSource.getDetailedCoin(id)
      emit(response)
    }
      .map<DetailedCoinDto, Result<DetailedCoin>> { Result.Success(it.toExternalModel(vs_currency)) }
      .onStart { emit(Result.Loading) }
      .catch {
        if (it is CancellationException) throw it  //  todo: consider if we need to return a result here
        else emit(Result.Error(it))
      }
      .flowOn(Dispatchers.IO)
  }
}