package com.haroof.watchlist

import com.haroof.data.model.Coin

sealed class WatchListUiState {

  object Loading : WatchListUiState()

  data class Error(val exception: Throwable?) : WatchListUiState()

  data class Success(val data: List<Coin>) : WatchListUiState()
}
