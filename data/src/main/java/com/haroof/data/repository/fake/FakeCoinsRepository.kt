package com.haroof.data.repository.fake

import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.model.asResult
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FakeCoinsRepository(
  private val shouldThrowError: Boolean,
) : CoinsRepository {

  @Inject constructor() : this(false)

  private val flow = MutableSharedFlow<List<Coin>>()

  override suspend fun getCoins(): Result<List<Coin>> {
    return if (shouldThrowError) Result.Error(IllegalStateException()) else Result.Success(FakeData.COINS)
  }

  override fun getWatchListCoinsFlow(): Flow<Result<List<Coin>>> = flow.asResult()

  suspend fun emit(coins: List<Coin>) {
    flow.emit(coins)
  }
}