package com.haroof.coin_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.data.model.Result.Error
import com.haroof.data.model.Result.Loading
import com.haroof.data.model.Result.Success
import com.haroof.data.repository.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
  coinsRepository: CoinsRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Loading)
  val uiState = _uiState.asStateFlow()

  //  todo: get coin id from nav args
  private val id = "bitcoin"

  init {
    viewModelScope.launch {
      val result = coinsRepository.getCoinById(
        id = id,
        vs_currency = "usd",
        sparkline = false
      )
      _uiState.value = when (result) {
        Loading -> CoinDetailUiState.Loading
        is Error -> CoinDetailUiState.Error(result.exception)
        is Success -> CoinDetailUiState.Success(result.data)
      }
    }
  }
}
