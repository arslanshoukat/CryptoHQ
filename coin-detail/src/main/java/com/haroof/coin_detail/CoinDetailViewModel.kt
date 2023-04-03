package com.haroof.coin_detail

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
import com.haroof.data.repository.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  coinsRepository: CoinsRepository,
  private val chartRepository: ChartRepository,
  private val watchListRepository: WatchListRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Loading)
  val uiState = _uiState.asStateFlow()

  // TODO: fix favorite state not loading correctly sometimes
  private var isFavorite = false

  private val coinId: String = CoinDetailArgs(savedStateHandle).coinId

  private var fetchChartJob: Job? = null

  init {
    viewModelScope.launch {
      val result = coinsRepository.getDetailedCoinById(
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
            chartData = emptyList(),
            isFavorite = isFavorite,
          )
        }
      }

      if (result is Success) fetchChartData()
    }

    watchListRepository.watchedCoinIds
      .onEach {
        isFavorite = it.contains(coinId)

        val prevUiState = _uiState.value.asSuccess() ?: return@onEach
        _uiState.value = prevUiState.copy(isFavorite = isFavorite)
      }
      .launchIn(viewModelScope)
  }

  private fun fetchChartData() {
    // if prev ui not success, return without fetching
    val prevUiState = _uiState.value.asSuccess() ?: return

    fetchChartJob?.cancel()
    fetchChartJob = viewModelScope.launch {
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
      _uiState.value = it.copy(selectedTimeFilter = timeFilter)
      fetchChartData()
    }
  }

  fun updateWatchListSelection(selected: Boolean) {
    viewModelScope.launch {
      if (selected) watchListRepository.addToWatchList(coinId)
      else watchListRepository.removeFromWatchList(coinId)
    }
  }
}
