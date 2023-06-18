package com.haroof.market

import app.cash.turbine.test
import app.cash.turbine.testIn
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
class MarketViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val userSettingsRepository = TestUserSettingsRepository()
  private val coinsRepository = TestCoinsRepository()
  private val getCoinsUseCase = GetCoinsUseCase(userSettingsRepository, coinsRepository)
  private lateinit var viewModel: MarketViewModel

  private val defaultCurrency = CurrencyTestData.USD

  @Before
  fun setup() {
    viewModel = MarketViewModel(getCoinsUseCase)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    assertEquals(MarketUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshIsSuccessful_stateIsSuccess() = runTest {
    viewModel.uiState.test {
      assertEquals(MarketUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(
        currencyCode = defaultCurrency.code,
        currencyUnit = defaultCurrency.unit
      )
      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })

      assertEquals(
        MarketUiState.Success(
          coinsToShow = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank },
          originalCoins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank },
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenDataRefreshIsSuccessfulButEmpty_stateIsEmpty() = runTest {
    viewModel.uiState.test {
      assertEquals(MarketUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(
        currencyCode = defaultCurrency.code,
        currencyUnit = defaultCurrency.unit
      )
      coinsRepository.sendCoins(emptyList())

      assertEquals(MarketUiState.Empty, awaitItem())
    }
  }

  @Test
  fun whenDataIsSortedByPriceForFirstTime_itIsSortedByAscendingOrder() = runTest {
    viewModel.uiState.test {
      assertEquals(MarketUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(
        currencyCode = defaultCurrency.code,
        currencyUnit = defaultCurrency.unit
      )
      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })
      assertEquals(
        MarketUiState.Success(
          coinsToShow = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank },
          originalCoins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank },
        ),
        awaitItem()
      )

      viewModel.sort(SortBy.PRICE)

      assertEquals(
        MarketUiState.Success(
          originalCoins = SimpleCoinTestData.LIST.sortedBy { it.currentPrice },
          coinsToShow = SimpleCoinTestData.LIST.sortedBy { it.currentPrice },
          sortBy = SortBy.PRICE,
          sortOrder = SortOrder.ASCENDING,
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun whenDataIsSortedByPriceAgain_itIsSortedByDescendingOrder() = runTest {
    viewModel.uiState.test {
      assertEquals(MarketUiState.Loading, awaitItem())

      userSettingsRepository.updateDefaultCurrency(
        currencyCode = defaultCurrency.code,
        currencyUnit = defaultCurrency.unit
      )
      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })
      assertEquals(
        MarketUiState.Success(
          coinsToShow = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank },
          originalCoins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank },
        ),
        awaitItem()
      )

      viewModel.sort(SortBy.PRICE)
      awaitItem()

      viewModel.sort(SortBy.PRICE)
      assertEquals(
        MarketUiState.Success(
          coinsToShow = SimpleCoinTestData.LIST.sortedByDescending { it.currentPrice },
          originalCoins = SimpleCoinTestData.LIST.sortedByDescending { it.currentPrice },
          sortBy = SortBy.PRICE,
          sortOrder = SortOrder.DESCENDING,
        ),
        awaitItem()
      )
    }
  }

  @Test
  fun onSearch_searchUiStateIsUpdated_andDataUpdatedAccordingToQuery() = runTest {
    val searchTurbine = viewModel.searchUiState.testIn(backgroundScope)
    val uiStateTurbine = viewModel.uiState.testIn(backgroundScope)

    assertEquals(MarketUiState.Loading, uiStateTurbine.awaitItem())
    assertEquals("", searchTurbine.awaitItem()) //  initially query in empty

    userSettingsRepository.updateDefaultCurrency(
      currencyCode = defaultCurrency.code,
      currencyUnit = defaultCurrency.unit
    )
    coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })

    val originalCoins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank }
    assertEquals(
      MarketUiState.Success(
        coinsToShow = originalCoins,
        originalCoins = originalCoins,
      ),
      uiStateTurbine.awaitItem()
    )

    val query = "bitcoin"
    viewModel.searchCoins(query)

    assertEquals(query, searchTurbine.awaitItem())
    assertEquals(
      MarketUiState.Success(
        coinsToShow = originalCoins.filter { it.name.contains(query, true) },
        originalCoins = originalCoins,
      ),
      uiStateTurbine.awaitItem()
    )
  }
}