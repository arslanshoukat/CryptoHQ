package com.haroof.market

import com.haroof.data.model.Coin

sealed class MarketUiState {

  object Loading : MarketUiState()

  data class Error(val exception: Throwable?) : MarketUiState()

  object Empty : MarketUiState()

  data class Success(
    val coins: List<Coin>,
    val sortBy: SortBy = SortBy.RANK,
    val sortOrder: SortOrder = SortOrder.ASCENDING,
  ) : MarketUiState()
}