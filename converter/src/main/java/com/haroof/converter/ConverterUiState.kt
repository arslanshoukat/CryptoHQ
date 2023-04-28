package com.haroof.converter

import com.haroof.domain.model.CurrencyUiModel

sealed class ConverterUiState {

  object Loading : ConverterUiState()

  data class Error(val exception: Throwable?) : ConverterUiState()

  data class Success(
    val sourceCurrency: CurrencyUiModel,
    val targetCurrency: CurrencyUiModel,
  ) : ConverterUiState()

  fun asSuccess() = this as? Success
}
