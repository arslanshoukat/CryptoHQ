package com.haroof.coin_detail

import com.haroof.common.model.TimeFilter
import com.haroof.data.model.DetailedCoin

sealed class CoinDetailUiState {

  object Loading : CoinDetailUiState()

  data class Error(val exception: Throwable?) : CoinDetailUiState()

  data class Success(
    val coin: DetailedCoin,
    val selectedTimeFilter: TimeFilter
  ) : CoinDetailUiState()
}
