package com.haroof.converter

import app.cash.turbine.test
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.CurrencyTestData
import com.haroof.testing.repository.TestCurrencyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConverterViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val currencyRepository = TestCurrencyRepository()
  private val getCurrenciesUseCase = GetCurrenciesUseCase(currencyRepository)
  private lateinit var viewModel: ConverterViewModel

  @Before
  fun setup() {
    viewModel = ConverterViewModel(getCurrenciesUseCase)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    assertEquals(ConverterUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshedSuccessfully_stateIsSuccess() = runTest {
    viewModel.uiState.test {
      assertEquals(ConverterUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })

      assertEquals(
        ConverterUiState.Success(
          from = CurrencyTestData.LIST.first { it.code.lowercase() == "btc" },
          to = CurrencyTestData.LIST.first { it.code.lowercase() == "usd" },
        ),
        awaitItem()
      )
    }
  }
}