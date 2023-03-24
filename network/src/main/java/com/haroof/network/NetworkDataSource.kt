package com.haroof.network

import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto

interface NetworkDataSource {

  suspend fun getCoins(
    vs_currency: String,
    ids: String,  //  comma separated ids, not a list
    sparkline: Boolean
  ): List<CoinDto>

  suspend fun getCoin(id: String): DetailedCoinDto
}