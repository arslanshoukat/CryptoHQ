package com.haroof.market

import com.haroof.domain.model.SimpleCoin

sealed class MarketUiState {

  object Loading : MarketUiState()

  data class Error(val exception: Throwable?) : MarketUiState()

  object Empty : MarketUiState()

  data class Success(
    val coinsToShow: List<SimpleCoin>,
    val originalCoins: List<SimpleCoin>,
    val sortBy: SortBy = SortBy.RANK,
    val sortOrder: SortOrder = SortOrder.ASCENDING,
  ) : MarketUiState()

  fun asSuccess() = this as? Success
}