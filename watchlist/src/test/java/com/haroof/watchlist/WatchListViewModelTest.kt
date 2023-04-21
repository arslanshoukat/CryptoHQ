package com.haroof.watchlist

import app.cash.turbine.test
import com.haroof.domain.GetWatchListCoinsUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.SimpleCoinTestData
import com.haroof.testing.repository.TestCoinsRepository
import com.haroof.testing.repository.TestWatchListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WatchListViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val coinsRepository = TestCoinsRepository()
  private val watchListRepository = TestWatchListRepository()
  private val getWatchListCoinsUseCase =
    GetWatchListCoinsUseCase(watchListRepository, coinsRepository)
  private lateinit var viewModel: WatchListViewModel

  @Before
  fun setup() {
    viewModel = WatchListViewModel(getWatchListCoinsUseCase)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    assertEquals(WatchListUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataLoadedSuccessfully_stateIsSuccess() = runTest {
    viewModel.uiState.test {
      assertEquals(WatchListUiState.Loading, awaitItem())

      val watchedCoinIds = listOf("bitcoin", "ethereum")
      watchListRepository.sendWatchedCoinsIds(watchedCoinIds)
      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })

      assertEquals(
        WatchListUiState.Success(SimpleCoinTestData.LIST.filter { it.id in watchedCoinIds }),
        awaitItem()
      )
    }
  }

  @Test
  fun whenDataLoadedSuccessfullyButEmpty_stateIsEmpty() = runTest {
    viewModel.uiState.test {
      assertEquals(WatchListUiState.Loading, awaitItem())

      val watchedCoinIds = emptyList<String>()
      watchListRepository.sendWatchedCoinsIds(watchedCoinIds)
      coinsRepository.sendCoins(SimpleCoinTestData.LIST.map { it.toDataModel() })

      assertEquals(WatchListUiState.Empty, awaitItem())
    }
  }
}