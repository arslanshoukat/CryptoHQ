package com.haroof.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {

  private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()
}
