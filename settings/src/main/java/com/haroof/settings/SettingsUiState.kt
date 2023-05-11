package com.haroof.settings

sealed class SettingsUiState {

  object Loading : SettingsUiState()

  data class Error(val exception: Throwable?) : SettingsUiState()

  data class Success(val currency: String) : SettingsUiState()
}
