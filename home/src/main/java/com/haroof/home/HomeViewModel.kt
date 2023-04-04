package com.haroof.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.common.model.Result
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.data.model.Coin
import com.haroof.data.repository.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val coinsRepository: CoinsRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  init {
    refresh()
  }

  private fun refresh() {
    coinsRepository.getCoins()
      .onEach { result: Result<List<Coin>> ->
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

  private fun getTopNGainersAndLosers(n: Int, coins: List<Coin>): List<Coin> {
    val gainers = coins.sortedByDescending { it.priceChangePercentage24h }
    val losers = coins.sortedBy { it.priceChangePercentage24h }

    val result = mutableListOf<Coin>()
    for (i in 0 until n) {
      result.add(gainers[i])
      result.add(losers[i])
    }

    return result.distinctBy { it.id }
  }
}
