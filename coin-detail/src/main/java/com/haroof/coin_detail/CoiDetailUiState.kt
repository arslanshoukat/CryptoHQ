package com.haroof.coin_detail

import com.haroof.data.model.Coin

sealed class CoinDetailUiState {

  object Loading : CoinDetailUiState()

  data class Error(val exception: Throwable?) : CoinDetailUiState()

  data class Success(val coin: Coin?) : CoinDetailUiState()
}
