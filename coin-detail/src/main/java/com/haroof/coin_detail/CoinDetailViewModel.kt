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

  private val _coinDetailUiState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Loading)
  val coinDetailUiState = _coinDetailUiState.asStateFlow()

  private val _chartUiState = MutableStateFlow(ChartUiState(loading = true))
  val chartUiState = _chartUiState.asStateFlow()

  val coinId: String = CoinDetailArgs(savedStateHandle).coinId

  init {
    getWatchableDetailedCoin(id = coinId)
      .onEach { result ->
        _coinDetailUiState.value = when (result) {
          Loading -> CoinDetailUiState.Loading
          is Error -> CoinDetailUiState.Error(result.exception)
          is Success -> CoinDetailUiState.Success(coin = result.data)
        }

        if (result is Success) fetchChartData()
      }
      .launchIn(viewModelScope)
  }

  private fun fetchChartData() {
    val prevChartUiState = _chartUiState.value

    getChartData(
      id = coinId,
      vs_currency = "usd",
      days = prevChartUiState.selectedTimeFilter.days,
      interval = prevChartUiState.selectedTimeFilter.interval,
    )
      .onEach { result ->
        _chartUiState.value = when (result) {
          Loading -> {
            ChartUiState(
              selectedTimeFilter = prevChartUiState.selectedTimeFilter,
              loading = true,
            )
          }
          is Error -> {
            ChartUiState(
              selectedTimeFilter = prevChartUiState.selectedTimeFilter,
              exception = result.exception,
            )
          }
          is Success -> {
            ChartUiState(
              selectedTimeFilter = prevChartUiState.selectedTimeFilter,
              chartData = result.data,
            )
          }
        }
      }
      .launchIn(viewModelScope)
  }

  fun updateTimeFilter(timeFilter: TimeFilter) {
    //  if user selected same filter, don't fetch data again
    if (_chartUiState.value.selectedTimeFilter == timeFilter) return

    _chartUiState.value = _chartUiState.value.copy(selectedTimeFilter = timeFilter)
    fetchChartData()
  }

  fun updateWatchListSelection(selected: Boolean) {
    viewModelScope.launch {
      if (selected) addCoinToWatchList(coinId)
      else removeCoinFromWatchList(coinId)
    }
  }
}
