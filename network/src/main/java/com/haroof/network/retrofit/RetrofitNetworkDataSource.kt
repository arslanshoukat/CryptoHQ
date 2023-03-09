package com.haroof.network.retrofit

import com.haroof.network.NetworkDataSource
import com.haroof.network.model.CoinDto
import retrofit2.http.GET

interface RetrofitNetworkDataSource : NetworkDataSource {

  @GET("coins/list")
  override suspend fun getCoins(): List<CoinDto>
}