package com.haroof.market

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
class MarketViewModel @Inject constructor(
  private val coinsRepository: CoinsRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow<MarketUiState>(MarketUiState.Loading)
  val uiState = _uiState.asStateFlow()

  init {
    refreshMarketData()
  }

  private fun refreshMarketData() {
    viewModelScope.launch {
      val result = coinsRepository.getCoins()
      _uiState.value = when (result) {
        Loading -> MarketUiState.Loading
        is Error -> MarketUiState.Error(result.exception)
        is Success -> {
          if (result.data.isEmpty()) MarketUiState.Empty
          else MarketUiState.Success(result.data)
        }
      }
    }
  }
}
