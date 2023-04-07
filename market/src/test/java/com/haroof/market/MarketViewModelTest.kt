package com.haroof.market

import app.cash.turbine.test
import com.haroof.domain.GetCoinsUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.SimpleCoinTestData
import com.haroof.testing.repository.TestCoinsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarketViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val coinsRepository = TestCoinsRepository()
  private val getCoinsUseCase = GetCoinsUseCase(coinsRepository)
  private lateinit var viewModel: MarketViewModel

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

      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })

      assertEquals(
        MarketUiState.Success(coins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank }),
        awaitItem()
      )
    }
  }

  @Test
  fun whenDataRefreshIsSuccessfulButEmpty_stateIsEmpty() = runTest {
    viewModel.uiState.test {
      assertEquals(MarketUiState.Loading, awaitItem())

      coinsRepository.sendCoins(emptyList())

      assertEquals(MarketUiState.Empty, awaitItem())
    }
  }

  @Test
  fun whenDataRefreshFailed_stateIsError() = runTest {
    assertTrue(viewModel.uiState.value is MarketUiState.Error)
  }

  @Test
  fun whenDataIsSortedByPriceForFirstTime_itIsSortedByAscendingOrder() = runTest {
    viewModel.uiState.test {
      assertEquals(MarketUiState.Loading, awaitItem())

      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })
      assertEquals(
        MarketUiState.Success(coins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank }),
        awaitItem()
      )

      viewModel.sort(SortBy.PRICE)

      assertEquals(
        MarketUiState.Success(
          coins = SimpleCoinTestData.LIST.sortedBy { it.currentPrice },
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

      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })
      assertEquals(
        MarketUiState.Success(coins = SimpleCoinTestData.LIST.sortedBy { it.marketCapRank }),
        awaitItem()
      )

      viewModel.sort(SortBy.PRICE)
      awaitItem()

      viewModel.sort(SortBy.PRICE)
      assertEquals(
        MarketUiState.Success(
          coins = SimpleCoinTestData.LIST.sortedByDescending { it.currentPrice },
          sortBy = SortBy.PRICE,
          sortOrder = SortOrder.DESCENDING,
        ),
        awaitItem()
      )
    }
  }
}