package com.haroof.home

import app.cash.turbine.test
import com.haroof.domain.GetCoinsUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.CurrencyTestData
import com.haroof.testing.data.SimpleCoinTestData
import com.haroof.testing.repository.TestCoinsRepository
import com.haroof.testing.repository.TestUserSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val userSettingsRepository = TestUserSettingsRepository()
  private val coinsRepository = TestCoinsRepository()
  private val getCoins = GetCoinsUseCase(userSettingsRepository, coinsRepository)
  private lateinit var viewModel: HomeViewModel

  private val defaultCurrency = CurrencyTestData.USD

  @Before
  fun setup() {
    viewModel = HomeViewModel(getCoins)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    assertEquals(HomeUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshIsSuccessful_stateIsSuccessWithData() = runTest {
    viewModel.uiState.test {
      assertEquals(HomeUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(
        currencyCode = defaultCurrency.code,
        currencyUnit = defaultCurrency.unit
      )
      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })

      assertEquals(
        HomeUiState.Success(
          gainersAndLosers = SimpleCoinTestData.GAINERS_AND_LOSERS,
          marketCoins = SimpleCoinTestData.LIST
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenDataRefreshIsSuccessfulButEmpty_stateIsEmpty() = runTest {
    viewModel.uiState.test {
      assertEquals(HomeUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(
        currencyCode = defaultCurrency.code,
        currencyUnit = defaultCurrency.unit
      )
      coinsRepository.sendCoins(emptyList())

      assertEquals(HomeUiState.Empty, awaitItem())
    }
  }
}