package com.haroof.home

import com.haroof.data.model.Coin

sealed class HomeUiState {

  object Loading : HomeUiState()

  data class Error(val exception: Throwable?) : HomeUiState()

  object Empty : HomeUiState()

  data class Success(
    val gainersAndLosers: List<Coin>,
    val marketCoins: List<Coin>,
  ) : HomeUiState()
}
