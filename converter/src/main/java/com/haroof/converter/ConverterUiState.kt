package com.haroof.converter

import com.haroof.domain.model.Currency

sealed class ConverterUiState {

  object Loading : ConverterUiState()

  data class Error(val exception: Throwable) : ConverterUiState()

  data class Success(
    val from: Currency,
    val to: Currency,
  ) : ConverterUiState()
}
