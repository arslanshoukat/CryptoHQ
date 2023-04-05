package com.haroof.home

import com.haroof.domain.model.SimpleCoin

sealed class HomeUiState {

  object Loading : HomeUiState()

  data class Error(val exception: Throwable?) : HomeUiState()

  object Empty : HomeUiState()

  data class Success(
    val gainersAndLosers: List<SimpleCoin>,
    val marketCoins: List<SimpleCoin>,
  ) : HomeUiState()
}
