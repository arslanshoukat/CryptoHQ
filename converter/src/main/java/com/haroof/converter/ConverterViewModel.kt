package com.haroof.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.domain.FetchCurrenciesUseCase
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.GetUserCurrenciesUseCase
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
class ConverterViewModel @Inject constructor(
  private val fetchCurrencies: FetchCurrenciesUseCase,
  private val getCurrencies: GetCurrenciesUseCase,
  private val getUserCurrencies: GetUserCurrenciesUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow<ConverterUiState>(ConverterUiState.Loading)
  val uiState = _uiState.asStateFlow()

  init {
    refreshCurrencies()
    getUserSelectedCurrencies()
  }

  private fun refreshCurrencies() {
    viewModelScope.launch {
      fetchCurrencies()
    }
  }

  private fun getUserSelectedCurrencies() {
    combine(
      getCurrencies(),
      getUserCurrencies()
    ) { currencies, userSelectedCurrencies ->
      if (currencies.isEmpty()) ConverterUiState.Loading
      else ConverterUiState.Success(
        from = currencies.first { it.code.lowercase() == userSelectedCurrencies.first },
        to = currencies.first { it.code.lowercase() == userSelectedCurrencies.second },
      )
    }
      .onEach { _uiState.value = it }
      .onStart { emit(ConverterUiState.Loading) }
      .launchIn(viewModelScope)
  }
}