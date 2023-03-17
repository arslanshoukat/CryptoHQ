package com.haroof.watchlist

import com.haroof.data.FakeData
import com.haroof.data.repository.fake.FakeCoinsRepository
import com.haroof.data.repository.fake.FakeWatchListRepository
import com.haroof.domain.GetWatchListCoinsUseCase
import com.haroof.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WatchListViewModelTest {
  // TODO: fix tests

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var coinsRepository: FakeCoinsRepository
  private lateinit var watchListRepository: FakeWatchListRepository
  private lateinit var getWatchListCoinsUseCase: GetWatchListCoinsUseCase
  private lateinit var viewModel: WatchListViewModel

  @Before
  fun setup() {
    coinsRepository = FakeCoinsRepository()
    watchListRepository = FakeWatchListRepository()
    getWatchListCoinsUseCase =
      GetWatchListCoinsUseCase(watchListRepository, coinsRepository)
    viewModel = WatchListViewModel(getWatchListCoinsUseCase)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    // Create an empty collector for the StateFlow
    val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

    assertEquals(WatchListUiState.Loading, viewModel.uiState.value)

    collectJob.cancel()
  }

  @Test
  fun whenDataLoadedSuccessfully_stateIsSuccess() = runTest {
    // Create an empty collector for the StateFlow
    val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

    assertEquals(WatchListUiState.Loading, viewModel.uiState.value)

    coinsRepository.emit(FakeData.COINS)
    assertEquals(WatchListUiState.Success(FakeData.COINS), viewModel.uiState.value)

    collectJob.cancel()
  }

  @Test
  fun whenDataLoadedSuccessfullyButEmpty_stateIsEmpty() = runTest {
    // Create an empty collector for the StateFlow
    val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

    assertEquals(WatchListUiState.Loading, viewModel.uiState.value)

    coinsRepository.emit(emptyList())
    assertEquals(WatchListUiState.Empty, viewModel.uiState.value)

    collectJob.cancel()
  }
}