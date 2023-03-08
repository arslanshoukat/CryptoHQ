package com.haroof.home

import com.haroof.data.model.Coin

sealed class HomeUiState {

  object Loading : HomeUiState()

  data class Success(val coins: List<Coin>) : HomeUiState()

  data class Error(val exception: Throwable?) : HomeUiState()
}
