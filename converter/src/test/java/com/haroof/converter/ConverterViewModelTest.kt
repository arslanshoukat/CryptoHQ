package com.haroof.converter

import app.cash.turbine.test
import com.haroof.domain.FetchCurrenciesUseCase
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.GetUserCurrenciesUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.CurrencyTestData
import com.haroof.testing.repository.TestCurrencyRepository
import com.haroof.testing.repository.TestUserSettingsRepository
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
  private val userSettingsRepository = TestUserSettingsRepository()

  private val fetchCurrenciesUseCase = FetchCurrenciesUseCase(currencyRepository)
  private val getCurrenciesUseCase = GetCurrenciesUseCase(currencyRepository)
  private val getUserCurrenciesUseCase = GetUserCurrenciesUseCase(userSettingsRepository)

  private lateinit var viewModel: ConverterViewModel

  @Before
  fun setup() {
    viewModel = ConverterViewModel(
      fetchCurrencies = fetchCurrenciesUseCase,
      getCurrencies = getCurrenciesUseCase,
      getUserCurrencies = getUserCurrenciesUseCase
    )
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    assertEquals(ConverterUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshedSuccessfully_stateIsSuccess() = runTest {
    viewModel.uiState.test {
      val sourceCurrencyCode = "btc"
      val targetCurrencyCode = "aed"

      assertEquals(ConverterUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(sourceCurrencyCode)
      userSettingsRepository.updateTargetCurrency(targetCurrencyCode)

      assertEquals(
        ConverterUiState.Success(
          sourceCurrency = CurrencyTestData.LIST.first { it.code.lowercase() == sourceCurrencyCode },
          targetCurrency = CurrencyTestData.LIST.first { it.code.lowercase() == targetCurrencyCode },
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenConvertingFromBitcoinToOtherCurrency_stateIsUpdatedWithValidValue() = runTest {
    viewModel.uiState.test {
      val sourceCurrencyCode = "btc"
      val targetCurrencyCode = "aed"

      assertEquals(ConverterUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(sourceCurrencyCode)
      userSettingsRepository.updateTargetCurrency(targetCurrencyCode)

      val sourceCurrency = CurrencyTestData.BTC
      val targetCurrency = CurrencyTestData.AED

      assertEquals(
        ConverterUiState.Success(
          sourceCurrency = sourceCurrency,
          targetCurrency = targetCurrency,
        ),
        awaitItem()
      )

      viewModel.convertCurrency("5.5")

      assertEquals(
        ConverterUiState.Success(
          sourceCurrency = sourceCurrency.copy(currentValue = 5.5),
          targetCurrency = targetCurrency.copy(currentValue = 550000.0),
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenConvertingBetweenCurrencies_stateIsUpdatedWithValidValue() = runTest {
    viewModel.uiState.test {
      val sourceCurrencyCode = "usd"
      val targetCurrencyCode = "aed"

      assertEquals(ConverterUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(sourceCurrencyCode)
      userSettingsRepository.updateTargetCurrency(targetCurrencyCode)

      val sourceCurrency = CurrencyTestData.USD
      val targetCurrency = CurrencyTestData.AED

      assertEquals(
        ConverterUiState.Success(
          sourceCurrency = sourceCurrency.copy(currentValue = 1.0),
          targetCurrency = targetCurrency.copy(currentValue = 4.0)
        ),
        awaitItem()
      )

      viewModel.convertCurrency("5.5")

      assertEquals(
        ConverterUiState.Success(
          sourceCurrency = sourceCurrency.copy(currentValue = 5.5),
          targetCurrency = targetCurrency.copy(currentValue = 22.0),
        ),
        awaitItem()
      )
    }
  }
}