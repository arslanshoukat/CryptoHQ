package com.haroof.settings

import app.cash.turbine.test
import com.haroof.domain.GetDefaultCurrencyUseCase
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.CurrencyTestData
import com.haroof.testing.repository.TestUserSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewModel: SettingsViewModel

  private val userSettingsRepository = TestUserSettingsRepository()
  private val getDefaultCurrencyUseCase = GetDefaultCurrencyUseCase(userSettingsRepository)

  @Before
  fun setup() {
    viewModel = SettingsViewModel(getDefaultCurrencyUseCase)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    viewModel.uiState.test {
      assertEquals(SettingsUiState.Loading, awaitItem())
    }
  }

  @Test
  fun whenUserSettingsAreLoaded_StateIsSuccess() = runTest {
    viewModel.uiState.test {
      assertEquals(SettingsUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(CurrencyTestData.USD.code)

      assertEquals(
        SettingsUiState.Success(
          currency = CurrencyTestData.USD.code,
        ),
        awaitItem()
      )
    }
  }
}