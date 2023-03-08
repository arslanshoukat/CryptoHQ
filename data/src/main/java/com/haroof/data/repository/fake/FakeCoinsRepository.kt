package com.haroof.data.repository.fake

import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.repository.CoinsRepository

class FakeCoinsRepository(
  private val shouldThrowError: Boolean = false,
) : CoinsRepository {

  override suspend fun getCoins(): Result<List<Coin>> {
    return if (shouldThrowError) Result.Error(IllegalStateException()) else Result.Success(FakeData.COINS)
  }
}