package com.haroof.watchlist

import com.haroof.domain.model.SimpleCoin

sealed class WatchListUiState {

  object Loading : WatchListUiState()

  data class Error(val exception: Throwable?) : WatchListUiState()

  object Empty : WatchListUiState()

  data class Success(val data: List<SimpleCoin>) : WatchListUiState()
}
