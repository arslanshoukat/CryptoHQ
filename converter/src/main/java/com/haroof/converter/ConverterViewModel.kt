package com.haroof.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.GetUserCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
  private val getCurrencies: GetCurrenciesUseCase,
  private val getUserCurrencies: GetUserCurrenciesUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow<ConverterUiState>(ConverterUiState.Loading)
  val uiState = _uiState.asStateFlow()

  init {
    fetchCurrencies()
  }

  private fun fetchCurrencies() {
    combine(
      getCurrencies(),
      getUserCurrencies()
    ) { result, userSelectedCurrencies ->
      _uiState.value = when (result) {
        Loading -> ConverterUiState.Loading
        is Error -> ConverterUiState.Error(result.exception)
        is Success -> ConverterUiState.Success(
          from = result.data.first { it.code.lowercase() == userSelectedCurrencies.first },
          to = result.data.first { it.code.lowercase() == userSelectedCurrencies.second },
        )
      }
    }
      .launchIn(viewModelScope)
  }
}