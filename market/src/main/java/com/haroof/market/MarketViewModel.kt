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
          //  if no data available, return empty state
          if (result.data.isEmpty()) MarketUiState.Empty
          //  else return data sorted by rank in ascending order
          else MarketUiState.Success(coins = result.data.sortedBy { it.marketCapRank })
        }
      }
    }
  }

  fun sort(updatedSortBy: SortBy) {
    (_uiState.value as? MarketUiState.Success)?.let { prevUiState ->
      val updatedSortOrder: SortOrder =
        if (prevUiState.sortBy == updatedSortBy) {
          //  if same sort parameter, just toggle sort order
          prevUiState.sortOrder.toggle()
        } else {
          //  else sort first by ascending order
          SortOrder.ASCENDING
        }

      val updatedCoins = when (updatedSortOrder) {
        SortOrder.ASCENDING -> {
          when (updatedSortBy) {
            SortBy.RANK -> prevUiState.coins.sortedBy { it.marketCapRank }
            SortBy.COIN -> prevUiState.coins.sortedBy { it.symbol }
            SortBy.PRICE_CHANGE_PERCENTAGE -> prevUiState.coins.sortedBy { it.priceChangePercentage24h }
            SortBy.PRICE -> prevUiState.coins.sortedBy { it.currentPrice }
          }
        }
        SortOrder.DESCENDING -> {
          when (updatedSortBy) {
            SortBy.RANK -> prevUiState.coins.sortedByDescending { it.marketCapRank }
            SortBy.COIN -> prevUiState.coins.sortedByDescending { it.symbol }
            SortBy.PRICE_CHANGE_PERCENTAGE -> prevUiState.coins.sortedByDescending { it.priceChangePercentage24h }
            SortBy.PRICE -> prevUiState.coins.sortedByDescending { it.currentPrice }
          }
        }
      }

      _uiState.value = prevUiState.copy(
        coins = updatedCoins,
        sortBy = updatedSortBy,
        sortOrder = updatedSortOrder,
      )
    }
  }
}
