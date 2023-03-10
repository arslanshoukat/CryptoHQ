package com.haroof.network.retrofit

import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RetrofitNetworkDataSource : NetworkDataSource {

  @GET("coins/markets")
  override suspend fun getCoins(@Query("vs_currency") vs_currency: String): List<CoinDto>
}