package com.haroof.data.repository.fake

import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.toExternalModel
import com.haroof.data.repository.CoinsRepository
import com.haroof.network.fake.FakeNetworkDataSource
import com.haroof.network.model.CoinDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeCoinsRepository @Inject constructor(
  private val networkDataSource: FakeNetworkDataSource,
) : CoinsRepository {

  override fun getCoins(vs_currency: String): Flow<List<Coin>> = flow {
    val coins = networkDataSource.getCoins(
      vs_currency = "usd",
      ids = emptyList(),
      sparkline = true
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
    val detailedCoin = networkDataSource.getDetailedCoin(id).toExternalModel(vs_currency)
    emit(detailedCoin)
  }.flowOn(Dispatchers.IO)
}