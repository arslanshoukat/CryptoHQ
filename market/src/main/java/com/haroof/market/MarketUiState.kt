package com.haroof.market

import com.haroof.data.model.Coin

sealed class MarketUiState {

  object Loading : MarketUiState()

  data class Success(val coins: List<Coin>) : MarketUiState()

  object Empty : MarketUiState()

  data class Error(val exception: Throwable?) : MarketUiState()
}