package com.haroof.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.coin_detail.navigation.CoinDetailArgs
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.common.model.TimeFilter
import com.haroof.domain.AddCoinToWatchListUseCase
import com.haroof.domain.GetChartDataUseCase
import com.haroof.domain.GetWatchableDetailedCoinUseCase
import com.haroof.domain.RemoveCoinFromWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  getWatchableDetailedCoin: GetWatchableDetailedCoinUseCase,
  private val getChartData: GetChartDataUseCase,
  private val addCoinToWatchList: AddCoinToWatchListUseCase,
  private val removeCoinFromWatchList: RemoveCoinFromWatchListUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val coinId: String = CoinDetailArgs(savedStateHandle).coinId

  init {
    getWatchableDetailedCoin(
      id = coinId,
      vs_currency = "usd",
    )
      .onEach { result ->
        _uiState.value = when (result) {
          Loading -> CoinDetailUiState.Loading
          is Error -> CoinDetailUiState.Error(result.exception)
          is Success -> {
            CoinDetailUiState.Success(
              coin = result.data,
              selectedTimeFilter = TimeFilter.ONE_WEEK,
              chartData = emptyList(),
            )
          }
        }

        if (result is Success) fetchChartData()
      }
      .launchIn(viewModelScope)
  }

  private fun fetchChartData() {
    // if prev ui not success, return without fetching
    val prevUiState = _uiState.value.asSuccess() ?: return

    getChartData(
      id = coinId,
      vs_currency = "usd",
      days = prevUiState.selectedTimeFilter.days,
      interval = prevUiState.selectedTimeFilter.interval,
    )
      .onEach { result ->
        when (result) {
          Loading -> {} // TODO: separate out loading & error handling for chart
          is Error -> {}
          is Success -> {
            _uiState.value = prevUiState.copy(
              chartData = result.data
            )
          }
        }
      }
      .launchIn(viewModelScope)
  }

  fun updateTimeFilter(timeFilter: TimeFilter) {
    _uiState.value.asSuccess()?.let {
      _uiState.value = it.copy(selectedTimeFilter = timeFilter)
      fetchChartData()
    }
  }

  fun updateWatchListSelection(selected: Boolean) {
    viewModelScope.launch {
      if (selected) addCoinToWatchList(coinId)
      else removeCoinFromWatchList(coinId)
    }
  }
}
