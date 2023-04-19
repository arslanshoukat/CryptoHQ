package com.haroof.converter

import androidx.lifecycle.ViewModel
import com.haroof.domain.sample_data.CurrencySampleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor() : ViewModel() {

  val uiState = MutableStateFlow<ConverterUiState>(ConverterUiState.Loading)

  init {
    uiState.value = ConverterUiState.Success(
      from = CurrencySampleData.BTC,
      to = CurrencySampleData.USD,
    )
  }
}