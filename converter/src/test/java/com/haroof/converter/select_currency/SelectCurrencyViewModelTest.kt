package com.haroof.converter.select_currency

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.haroof.converter.navigation.isSourceCurrencyArg
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.GetUserCurrenciesUseCase
import com.haroof.domain.UpdateUserSelectedCurrencyUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.CurrencyTestData
import com.haroof.testing.repository.TestCurrencyRepository
import com.haroof.testing.repository.TestUserSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SelectCurrencyViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val currencyRepository = TestCurrencyRepository()
  private val userSettingsRepository = TestUserSettingsRepository()
  private val getCurrenciesUseCase = GetCurrenciesUseCase(currencyRepository)
  private val getUserCurrenciesUseCase = GetUserCurrenciesUseCase(userSettingsRepository)
  private val updateUserSelectedCurrencyUseCase =
    UpdateUserSelectedCurrencyUseCase(userSettingsRepository)

  private lateinit var viewModel: SelectCurrencyViewModel

  @Before
  fun setUp() {
    viewModel = SelectCurrencyViewModel(
      savedStateHandle = SavedStateHandle(mapOf(isSourceCurrencyArg to false)),
      getCurrencies = getCurrenciesUseCase,
      getUserCurrencies = getUserCurrenciesUseCase,
      updateUserSelectedCurrency = updateUserSelectedCurrencyUseCase,
    )
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    assertEquals(SelectCurrencyUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataLoadedSuccessfully_stateIsSuccess() = runTest {
    viewModel.uiState.test {
      val selectedCurrencyCode = CurrencyTestData.USD.code
      val otherCurrencyCode = CurrencyTestData.BTC.code

      assertEquals(SelectCurrencyUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(otherCurrencyCode)
      userSettingsRepository.updateToCurrency(selectedCurrencyCode)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrencyCode
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenCurrencyIsSelected_stateIsUpdated() = runTest {
    viewModel.uiState.test {
      val selectedCurrencyCode = CurrencyTestData.USD.code
      val otherCurrencyCode = CurrencyTestData.BTC.code

      assertEquals(SelectCurrencyUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(otherCurrencyCode)
      userSettingsRepository.updateToCurrency(selectedCurrencyCode)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrencyCode
        ),
        awaitItem()
      )

      val newlySelectedCurrencyCode = CurrencyTestData.AED.code
      viewModel.selectCurrency(newlySelectedCurrencyCode)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = newlySelectedCurrencyCode
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenSameCurrencyIsSelected_stateNotUpdated() = runTest {
    viewModel.uiState.test {
      val selectedCurrencyCode = CurrencyTestData.USD.code
      val otherCurrencyCode = CurrencyTestData.BTC.code

      assertEquals(SelectCurrencyUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(otherCurrencyCode)
      userSettingsRepository.updateToCurrency(selectedCurrencyCode)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrencyCode
        ),
        awaitItem()
      )

      viewModel.selectCurrency(selectedCurrencyCode)
      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrencyCode
        ),
        viewModel.uiState.value
      )

    }
  }
}