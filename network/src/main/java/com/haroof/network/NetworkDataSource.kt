package com.haroof.network

import com.haroof.network.model.CoinDto

interface NetworkDataSource {

  suspend fun getCoins(vs_currency: String): List<CoinDto>
}