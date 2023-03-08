package com.haroof.data.repository

import com.haroof.data.model.Coin
import com.haroof.data.model.Result

interface CoinsRepository {

  suspend fun getCoins(): Result<List<Coin>>
}