package com.haroof.coin_detail

import com.haroof.common.model.TimeFilter
import com.haroof.domain.model.WatchableDetailedCoin

sealed class CoinDetailUiState {

  object Loading : CoinDetailUiState()

  data class Error(val exception: Throwable?) : CoinDetailUiState()

  data class Success(
    val coin: WatchableDetailedCoin,
    val selectedTimeFilter: TimeFilter,
    val chartData: List<Double>,
  ) : CoinDetailUiState()

  fun asSuccess() = this as? Success
}
