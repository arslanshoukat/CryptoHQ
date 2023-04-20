package com.haroof.converter

import com.haroof.domain.model.CurrencyUiModel

sealed class ConverterUiState {

  object Loading : ConverterUiState()

  data class Error(val exception: Throwable?) : ConverterUiState()

  data class Success(
    val from: CurrencyUiModel,
    val to: CurrencyUiModel,
  ) : ConverterUiState()
}
