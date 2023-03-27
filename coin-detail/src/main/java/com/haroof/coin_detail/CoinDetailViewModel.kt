package com.haroof.coin_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.coin_detail.navigation.CoinDetailArgs
import com.haroof.common.model.TimeFilter
import com.haroof.data.model.Result.Error
import com.haroof.data.model.Result.Loading
import com.haroof.data.model.Result.Success
import com.haroof.data.repository.ChartRepository
import com.haroof.data.repository.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  coinsRepository: CoinsRepository,
  private val chartRepository: ChartRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val coinId: String = CoinDetailArgs(savedStateHandle).coinId

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
            selectedTimeFilter = TimeFilter.ONE_WEEK,
            chartData = emptyList()
          )
        }
      }

      if (result is Success) fetchChartData()
    }
  }

  private fun fetchChartData() {
    // if prev ui not success, return without fetching
    val prevUiState = _uiState.value.asSuccess() ?: return

    viewModelScope.launch {
      val result = chartRepository.getChartData(
        id = coinId,
        vs_currency = "usd",
        days = prevUiState.selectedTimeFilter.days,
        interval = prevUiState.selectedTimeFilter.interval,
      )
      when (result) {
        Loading -> {}
        is Error -> {}
        is Success -> {
          _uiState.value = prevUiState.copy(
            chartData = result.data.prices.map { it[1] }.dropLast(1)  //  todo: improve drop logic
          )
        }
      }
    }
  }

  fun updateTimeFilter(timeFilter: TimeFilter) {
    _uiState.value.asSuccess()?.let {
      Log.i("CoinDetailViewModel", "selectedTimeFilter: $timeFilter")
      _uiState.value = it.copy(selectedTimeFilter = timeFilter)
      fetchChartData()
    }
  }
}
