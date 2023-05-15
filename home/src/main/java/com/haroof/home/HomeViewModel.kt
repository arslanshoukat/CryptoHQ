package com.haroof.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.domain.GetCoinsUseCase
import com.haroof.domain.model.SimpleCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getCoins: GetCoinsUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  init {
    refresh()
  }

  private fun refresh() {
    getCoins(sparkline = true)
      .onEach { result ->
        _uiState.value = when (result) {
          Loading -> HomeUiState.Loading
          is Error -> HomeUiState.Error(result.exception)
          is Success -> {
            val coins = result.data
            if (coins.isEmpty()) HomeUiState.Empty
            else {
              HomeUiState.Success(
                gainersAndLosers = getTopNGainersAndLosers(3, coins),
                marketCoins = coins.sortedBy { it.marketCapRank }.take(10),
              )
            }
          }
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getTopNGainersAndLosers(n: Int, coins: List<SimpleCoin>): List<SimpleCoin> {
    val gainers = coins.sortedByDescending { it.priceChangePercentage24h }
    val losers = coins.sortedBy { it.priceChangePercentage24h }

    val result = mutableListOf<SimpleCoin>()
    for (i in 0 until n) {
      result.add(gainers[i])
      result.add(losers[i])
    }

    return result.distinctBy { it.id }
  }
}
