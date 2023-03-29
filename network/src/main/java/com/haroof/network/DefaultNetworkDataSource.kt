package com.haroof.network

import com.haroof.network.model.ChartDataDto
import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto
import com.haroof.network.retrofit.RetrofitCryptoHqApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default implementation of network data source backed by retrofit based CryptoHq Api.
 */
@Singleton
class DefaultNetworkDataSource @Inject constructor(
  private val api: RetrofitCryptoHqApi
) : NetworkDataSource {

  override suspend fun getCoins(
    vs_currency: String,
    ids: String,
    sparkline: Boolean
  ): List<CoinDto> {
    return api.getCoins(
      vs_currency = vs_currency,
      ids = ids,
      sparkline = sparkline,
    )
  }

  override suspend fun getDetailedCoin(id: String): DetailedCoinDto {
    return api.getCoin(id = id)
  }

  override suspend fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): ChartDataDto {
    return api.getChartData(
      id = id,
      vs_currency = vs_currency,
      days = days,
      interval = interval,
    )
  }
}