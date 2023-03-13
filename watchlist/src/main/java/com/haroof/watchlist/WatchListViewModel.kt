package com.haroof.watchlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchListViewModel : ViewModel() {

  private val _state = MutableStateFlow(WatchListUiState.Loading)
  val state = _state.asStateFlow()
}
