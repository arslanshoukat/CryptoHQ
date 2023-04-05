package com.haroof.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.domain.GetWatchListCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
  getWatchListCoins: GetWatchListCoinsUseCase
) : ViewModel() {

  val uiState: StateFlow<WatchListUiState> = getWatchListCoins()
    .map { result ->
      when (result) {
        Loading -> WatchListUiState.Loading
        is Error -> WatchListUiState.Error(result.exception)
        is Success -> {
          if (result.data.isEmpty()) WatchListUiState.Empty
          else WatchListUiState.Success(result.data)
        }
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = WatchListUiState.Loading
    )
}
