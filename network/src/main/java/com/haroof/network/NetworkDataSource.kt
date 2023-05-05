package com.haroof.network

import com.haroof.network.model.ChartDataDto
import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto
import com.haroof.network.model.GetExchangeRatesResponse

interface NetworkDataSource {

  suspend fun getCoins(
    vs_currency: String,
    ids: List<String>,
    sparkline: Boolean
  ): List<CoinDto>

  suspend fun getDetailedCoin(id: String): DetailedCoinDto

  suspend fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): ChartDataDto

  suspend fun getExchangeRates(): GetExchangeRatesResponse
}