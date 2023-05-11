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

/**
 * Test class to unit test viewmodel when user is updating default currency that is used
 * to configure vs_currency in API calls.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SelectDefaultCurrencyViewModelTest {

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
          isDefaultCurrencyArg to true,
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
      assertEquals(SelectCurrencyUiState.Loading, awaitItem())

      val selectedCurrencyCode = CurrencyTestData.AED.code

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateDefaultCurrency(selectedCurrencyCode)

      val selectableCurrencies = CurrencyTestData.LIST.filterNot {
        it.type.equals("commodity", true)
      }

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = selectableCurrencies,
          selectedCurrencyCode = selectedCurrencyCode
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenCurrencyIsSelected_stateIsUpdated() = runTest {
    viewModel.uiState.test {
      assertEquals(SelectCurrencyUiState.Loading, awaitItem())

      val selectedCurrencyCode = CurrencyTestData.AED.code

      currencyRepository.sendCurrencies(CurrencyTestData.LIST.map { it.toDataModel() })
      userSettingsRepository.updateDefaultCurrency(selectedCurrencyCode)

      val selectableCurrencies = CurrencyTestData.LIST.filterNot {
        it.type.equals("commodity", true)
      }

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = selectableCurrencies,
          selectedCurrencyCode = selectedCurrencyCode
        ),
        awaitItem()
      )

      val newlySelectedCurrencyCode = CurrencyTestData.PKR.code
      viewModel.selectCurrency(newlySelectedCurrencyCode)

      assertEquals(
        SelectCurrencyUiState.Success(
          selectableCurrencies = selectableCurrencies,
          selectedCurrencyCode = newlySelectedCurrencyCode
        ),
        awaitItem()
      )
    }
  }
}