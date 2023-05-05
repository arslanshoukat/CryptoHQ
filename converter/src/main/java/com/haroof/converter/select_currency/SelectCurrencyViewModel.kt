package com.haroof.converter.select_currency

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.converter.navigation.SelectCurrencyArgs
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.GetUserCurrenciesUseCase
import com.haroof.domain.UpdateUserSelectedCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCurrencyViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  getCurrencies: GetCurrenciesUseCase,
  getUserCurrencies: GetUserCurrenciesUseCase,
  private val updateUserSelectedCurrency: UpdateUserSelectedCurrencyUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow<SelectCurrencyUiState>(SelectCurrencyUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val isSourceCurrency: Boolean = SelectCurrencyArgs(savedStateHandle).isSourceCurrency

  init {
    combine(
      getCurrencies(),
      getUserCurrencies()
    ) { currencies, userSelectedCurrencies ->
      if (currencies.isEmpty()) SelectCurrencyUiState.Loading
      else {
        val selectedCurrencyCode =
          if (isSourceCurrency) userSelectedCurrencies.first else userSelectedCurrencies.second
        val otherCurrencyCode =
          if (isSourceCurrency) userSelectedCurrencies.second else userSelectedCurrencies.first

        SelectCurrencyUiState.Success(
          selectableCurrencies = currencies.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrencyCode
        )
      }
    }
      .onEach { _uiState.value = it }
      .onStart { emit(SelectCurrencyUiState.Loading) }
      .launchIn(viewModelScope)
  }

  fun selectCurrency(currencyCode: String) {
    //  do not update if reselected same currency
    if (currencyCode == uiState.value.asSuccess()?.selectedCurrencyCode) return

    viewModelScope.launch {
      updateUserSelectedCurrency(
        sourceCurrency = isSourceCurrency,
        currencyCode = currencyCode
      )
    }
  }
}