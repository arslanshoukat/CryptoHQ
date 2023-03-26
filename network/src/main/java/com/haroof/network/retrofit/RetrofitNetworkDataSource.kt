package com.haroof.network.retrofit

import com.haroof.network.NetworkDataSource
import com.haroof.network.model.ChartDataDto
import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitNetworkDataSource : NetworkDataSource {

  @GET("coins/markets")
  override suspend fun getCoins(
    @Query("vs_currency") vs_currency: String,
    @Query("ids") ids: String,  //  comma separated ids, not a list
    @Query("sparkline") sparkline: Boolean,
  ): List<CoinDto>

  @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
  override suspend fun getCoin(
    @Path("id") id: String
  ): DetailedCoinDto

  @GET("coins/{id}/market_chart")
  override suspend fun getChartData(
    @Path("id") id: String,
    @Query("vs_currency") vs_currency: String,
    @Query("days") days: String,
    @Query("interval") interval: String
  ): ChartDataDto
}