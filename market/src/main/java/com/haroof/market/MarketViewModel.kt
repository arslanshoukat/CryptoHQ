package com.haroof.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.domain.GetCoinsUseCase
import com.haroof.domain.model.SimpleCoin
import com.haroof.market.MarketUiState.Empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
  private val getCoins: GetCoinsUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow<MarketUiState>(MarketUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val _searchUiState = MutableStateFlow("")
  val searchUiState = _searchUiState.asStateFlow()

  init {
    refreshMarketData()
  }

  private fun refreshMarketData() {
    getCoins()
      .onEach { result ->
        _uiState.value = when (result) {
          Loading -> MarketUiState.Loading
          is Error -> MarketUiState.Error(result.exception)
          is Success -> {
            //  if no data available, return empty state
            if (result.data.isEmpty()) Empty
            //  else return data sorted by rank in ascending order
            else {
              val coins = result.data.sortedBy { it.marketCapRank }
              MarketUiState.Success(
                coinsToShow = coins,
                originalCoins = coins,
              )
            }
          }
        }
      }
      .launchIn(viewModelScope)
  }

  fun searchCoins(newQuery: String) {
    _searchUiState.value = newQuery

    _uiState.value.asSuccess()?.let { prevUiState ->
      _uiState.value = prevUiState.copy(
        coinsToShow = prevUiState.originalCoins.filter { it.name.contains(newQuery, true) },
      )
    }
  }

  fun sort(updatedSortBy: SortBy) {
    _uiState.value.asSuccess()?.let { prevUiState ->
      val updatedSortOrder: SortOrder =
        if (prevUiState.sortBy == updatedSortBy) {
          //  if same sort parameter, just toggle sort order
          prevUiState.sortOrder.toggle()
        } else {
          //  else sort first by ascending order
          SortOrder.ASCENDING
        }

      val updatedCoins = getSortedCoins(prevUiState.originalCoins, updatedSortOrder, updatedSortBy)

      _uiState.value = prevUiState.copy(
        coinsToShow = updatedCoins,
        originalCoins = updatedCoins,
        sortBy = updatedSortBy,
        sortOrder = updatedSortOrder,
      )
    }
  }

  private fun getSortedCoins(
    coins: List<SimpleCoin>,
    sortOrder: SortOrder,
    sortBy: SortBy,
  ) = when (sortOrder) {
    SortOrder.ASCENDING -> {
      when (sortBy) {
        SortBy.RANK -> coins.sortedBy { it.marketCapRank }
        SortBy.COIN -> coins.sortedBy { it.symbol }
        SortBy.PRICE_CHANGE_PERCENTAGE -> coins.sortedBy { it.priceChangePercentage24h }
        SortBy.PRICE -> coins.sortedBy { it.currentPrice }
      }
    }
    SortOrder.DESCENDING -> {
      when (sortBy) {
        SortBy.RANK -> coins.sortedByDescending { it.marketCapRank }
        SortBy.COIN -> coins.sortedByDescending { it.symbol }
        SortBy.PRICE_CHANGE_PERCENTAGE -> coins.sortedByDescending { it.priceChangePercentage24h }
        SortBy.PRICE -> coins.sortedByDescending { it.currentPrice }
      }
    }
  }
}
