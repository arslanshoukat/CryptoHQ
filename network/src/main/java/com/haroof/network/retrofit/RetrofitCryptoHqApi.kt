package com.haroof.network.retrofit

import com.haroof.network.model.ChartDataDto
import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto
import com.haroof.network.model.GetExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitCryptoHqApi {

  @GET("coins/markets")
  suspend fun getCoins(
    @Query("vs_currency") vs_currency: String,
    @Query("ids") ids: String,  //  comma separated ids, not a list
    @Query("sparkline") sparkline: Boolean,
  ): List<CoinDto>

  @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
  suspend fun getCoin(
    @Path("id") id: String
  ): DetailedCoinDto

  @GET("coins/{id}/market_chart")
  suspend fun getChartData(
    @Path("id") id: String,
    @Query("vs_currency") vs_currency: String,
    @Query("days") days: String,
    @Query("interval") interval: String
  ): ChartDataDto

  @GET("exchange_rates")
  suspend fun getExchangeRates(): GetExchangeRatesResponse
}