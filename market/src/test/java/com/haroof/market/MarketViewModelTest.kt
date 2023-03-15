package com.haroof.market

import com.haroof.data.FakeData
import com.haroof.data.repository.fake.FakeCoinsRepository
import com.haroof.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarketViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun stateIsInitiallyLoading() = runTest {
    val viewModel = MarketViewModel(FakeCoinsRepository())
    assertEquals(MarketUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshIsSuccessful_stateIsSuccess() = runTest {
    val viewModel = MarketViewModel(FakeCoinsRepository())
    assertEquals(
      MarketUiState.Success(FakeData.COINS.sortedBy { it.marketCapRank }),
      viewModel.uiState.value
    )
  }

  @Test
  fun whenDataRefreshIsSuccessfulButEmpty_stateIsEmpty() = runTest {
    val viewModel = MarketViewModel(
      FakeCoinsRepository(
        shouldThrowError = false,
        shouldReturnEmpty = true
      )
    )
    assertEquals(MarketUiState.Empty, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshFailed_stateIsError() = runTest {
    val viewModel = MarketViewModel(
      FakeCoinsRepository(
        shouldThrowError = true,
        shouldReturnEmpty = false
      )
    )
    assertTrue(viewModel.uiState.value is MarketUiState.Error)
  }

  @Test
  fun whenDataIsSortedByPriceForFirstTime_itIsSortedByAscendingOrder() {
    val viewModel = MarketViewModel(FakeCoinsRepository())
    viewModel.sort(SortBy.PRICE)
    assertEquals(
      MarketUiState.Success(
        coins = FakeData.COINS.sortedBy { it.currentPrice },
        sortBy = SortBy.PRICE,
        sortOrder = SortOrder.ASCENDING,
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun whenDataIsSortedByPriceAgain_itIsSortedByDescendingOrder() {
    val viewModel = MarketViewModel(FakeCoinsRepository())
    viewModel.sort(SortBy.PRICE)
    viewModel.sort(SortBy.PRICE)
    assertEquals(
      MarketUiState.Success(
        coins = FakeData.COINS.sortedByDescending { it.currentPrice },
        sortBy = SortBy.PRICE,
        sortOrder = SortOrder.DESCENDING,
      ),
      viewModel.uiState.value
    )
  }
}