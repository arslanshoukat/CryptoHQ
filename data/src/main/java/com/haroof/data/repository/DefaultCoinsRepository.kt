package com.haroof.data.repository

import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultCoinsRepository @Inject constructor(
  private val networkDataSource: NetworkDataSource
) : CoinsRepository {

  override fun getCoins(
    vs_currency: String,
    sparkline: Boolean
  ): Flow<List<Coin>> = flow {
    val coins = networkDataSource.getCoins(
      vs_currency = vs_currency,
      ids = emptyList(),
      sparkline = sparkline
    ).map(CoinDto::toExternalModel)
    emit(coins)
  }.flowOn(Dispatchers.IO)

  override fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<List<Coin>> = flow {
    val coins = networkDataSource.getCoins(
      vs_currency = vs_currency,
      ids = ids,
      sparkline = sparkline
    ).map(CoinDto::toExternalModel)
    emit(coins)
  }.flowOn(Dispatchers.IO)

  override fun getDetailedCoinById(
    id: String,
    vs_currency: String,
  ): Flow<DetailedCoin> = flow {
    val coin = networkDataSource.getDetailedCoin(id).toExternalModel(vs_currency)
    emit(coin)
  }.flowOn(Dispatchers.IO)
}