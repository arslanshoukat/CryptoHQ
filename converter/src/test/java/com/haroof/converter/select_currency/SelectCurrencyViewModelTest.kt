package com.haroof.converter.select_currency

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.haroof.converter.navigation.isDefaultCurrencyArg
import com.haroof.converter.navigation.isSourceCurrencyArg
import com.haroof.domain.GetCurrenciesUseCase
import com.haroof.domain.GetDefaultCurrencyUseCase
import com.haroof.domain.GetUserCurrenciesUseCase
import com.haroof.domain.UpdateDefaultCurrencyUseCase
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
  private val getDefaultCurrencyUseCase = GetDefaultCurrencyUseCase(userSettingsRepository)
  private val updateUserSelectedCurrencyUseCase =
    UpdateUserSelectedCurrencyUseCase(userSettingsRepository)
  private val updateDefaultCurrencyUseCase = UpdateDefaultCurrencyUseCase(userSettingsRepository)

  private lateinit var viewModel: SelectCurrencyViewModel

  @Before
  fun setUp() {
    viewModel = SelectCurrencyViewModel(
      savedStateHandle = SavedStateHandle(
        mapOf(
          isDefaultCurrencyArg to false,
          isSourceCurrencyArg to false
        )
      ),
      getCurrencies = getCurrenciesUseCase,
      getUserCurrencies = getUserCurrenciesUseCase,
      getDefaultCurrency = getDefaultCurrencyUseCase,
      updateUserSelectedCurrency = updateUserSelectedCurrencyUseCase,
      updateDefaultCurrency = updateDefaultCurrencyUseCase,
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
      userSettingsRepository.updateTargetCurrency(selectedCurrencyCode)

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
      userSettingsRepository.updateTargetCurrency(selectedCurrencyCode)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrencyCode
        ),
        awaitItem()
      )

      val newlySelectedCurrency = CurrencyTestData.AED
      viewModel.selectCurrency(
        currencyCode = newlySelectedCurrency.code,
        currencyUnit = newlySelectedCurrency.unit
      )

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = newlySelectedCurrency.code
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenSameCurrencyIsSelected_stateNotUpdated() = runTest {
    viewModel.uiState.test {
      val selectedCurrency = CurrencyTestData.USD
      val otherCurrencyCode = CurrencyTestData.BTC.code

      assertEquals(SelectCurrencyUiState.Loading, awaitItem())

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateSourceCurrency(otherCurrencyCode)
      userSettingsRepository.updateTargetCurrency(selectedCurrency.code)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrency.code
        ),
        awaitItem()
      )

      viewModel.selectCurrency(
        currencyCode = selectedCurrency.code,
        currencyUnit = selectedCurrency.unit
      )
      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST.filter { it.code != otherCurrencyCode },
          selectedCurrencyCode = selectedCurrency.code
        ),
        viewModel.uiState.value
      )

    }
  }
}