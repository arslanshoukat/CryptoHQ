package com.haroof.converter.select_currency

import com.haroof.domain.model.CurrencyUiModel

sealed class SelectCurrencyUiState {

  object Loading : SelectCurrencyUiState()

  data class Success(
    val selectableCurrencies: List<CurrencyUiModel>,
    val selectedCurrencyCode: String
  ) : SelectCurrencyUiState()

  fun asSuccess() = this as? Success
}
