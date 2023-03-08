package com.haroof.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.data.model.Result.Error
import com.haroof.data.model.Result.Loading
import com.haroof.data.model.Result.Success
import com.haroof.data.repository.CoinsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
  private val coinsRepository: CoinsRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  fun refresh() {
    viewModelScope.launch {
      _uiState.value = when (val result = coinsRepository.getCoins()) {
        Loading -> HomeUiState.Loading
        is Success -> HomeUiState.Success(result.data)
        is Error -> HomeUiState.Error(result.exception)
      }
    }
  }
}
