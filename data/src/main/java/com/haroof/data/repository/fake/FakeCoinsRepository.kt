package com.haroof.data.repository.fake

import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.data.model.Result
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FakeCoinsRepository(
  private val shouldThrowError: Boolean,
  private val shouldReturnEmpty: Boolean,
) : CoinsRepository {

  @Inject constructor() : this(false, false)

  private val flow = MutableSharedFlow<List<Coin>>()

  override suspend fun getCoins(): Result<List<Coin>> {
    return if (shouldThrowError) Result.Error(IllegalStateException())
    else Result.Success(if (shouldReturnEmpty) emptyList() else FakeData.COINS)
  }

  override suspend fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Result<List<Coin>> {
    return if (shouldThrowError) Result.Error(IllegalStateException())
    else Result.Success(
      if (shouldReturnEmpty) emptyList()
      else FakeData.COINS.filter { ids.contains(it.name) }
    )
  }

  override suspend fun getCoinById(
    id: String,
    vs_currency: String,
    sparkline: Boolean
  ): Result<Coin?> {
    return if (shouldThrowError) Result.Error(IllegalStateException())
    else Result.Success(FakeData.COINS.firstOrNull { it.id == id })
  }

  suspend fun emit(coins: List<Coin>) {
    flow.emit(coins)
  }
}