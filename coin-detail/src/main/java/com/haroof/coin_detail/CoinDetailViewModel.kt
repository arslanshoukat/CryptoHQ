package com.haroof.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.common.model.TimeFilter
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
  savedStateHandle: SavedStateHandle,
  coinsRepository: CoinsRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val coinId: String = savedStateHandle["coinId"]!!

  init {
    viewModelScope.launch {
      val result = coinsRepository.getCoinById(
        id = coinId,
        vs_currency = "usd"
      )
      _uiState.value = when (result) {
        Loading -> CoinDetailUiState.Loading
        is Error -> CoinDetailUiState.Error(result.exception)
        is Success -> {
          CoinDetailUiState.Success(
            coin = result.data,
            selectedTimeFilter = TimeFilter.ONE_WEEK
          )
        }
      }
    }
  }

  fun updateTimeFilter(timeFilter: TimeFilter) {
    // TODO: fix filter selection
    (_uiState.value as? CoinDetailUiState.Success)?.let { prevResult ->
      _uiState.value = prevResult.copy(selectedTimeFilter = timeFilter)
    }
  }
}
