package com.haroof.data.repository.fake

import com.haroof.common.model.Result
import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeCoinsRepository(
  private val shouldThrowError: Boolean,
  private val shouldReturnEmpty: Boolean,
) : CoinsRepository {
  // TODO: fix fake repo for tests

  @Inject constructor() : this(false, false)

  private val flow = MutableSharedFlow<List<Coin>>()

  override fun getCoins(): Flow<Result<List<Coin>>> {
    return flowOf(
      if (shouldThrowError) Result.Error(IllegalStateException())
      else Result.Success(if (shouldReturnEmpty) emptyList() else FakeData.COINS)
    )
  }

  override fun getCoinsByIds(
    ids: List<String>,
    vs_currency: String,
    sparkline: Boolean
  ): Flow<Result<List<Coin>>> {
    return flowOf(if (shouldThrowError) Result.Error(IllegalStateException())
    else Result.Success(
      if (shouldReturnEmpty) emptyList()
      else FakeData.COINS.filter { ids.contains(it.name) }
    ))
  }

  override fun getDetailedCoinById(id: String, vs_currency: String): Flow<Result<DetailedCoin>> {
    return flowOf(
      if (shouldThrowError) Result.Error(IllegalStateException())
      else Result.Success(FakeData.DETAILED_COINS.first { it.id == id })
    )
  }

  suspend fun emit(coins: List<Coin>) {
    flow.emit(coins)
  }
}