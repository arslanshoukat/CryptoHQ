package com.haroof.coin_detail

import com.haroof.domain.model.WatchableDetailedCoin

sealed class CoinDetailUiState {

  object Loading : CoinDetailUiState()

  data class Error(val exception: Throwable?) : CoinDetailUiState()

  data class Success(val coin: WatchableDetailedCoin) : CoinDetailUiState()

  fun asSuccess() = this as? Success
}
