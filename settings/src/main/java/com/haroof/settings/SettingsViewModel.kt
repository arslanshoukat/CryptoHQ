package com.haroof.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.domain.GetDefaultCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  getDefaultCurrency: GetDefaultCurrencyUseCase
) : ViewModel() {

  @OptIn(ExperimentalCoroutinesApi::class)
  val uiState: StateFlow<SettingsUiState> = getDefaultCurrency()
    .mapLatest { SettingsUiState.Success(currency = it) }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000L),
      initialValue = SettingsUiState.Loading,
    )
}
